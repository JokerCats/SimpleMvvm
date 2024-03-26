package net.jkcats.simplemvvm.simple.task

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import net.jkcats.simplemvvm.network.api.RequestService
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(private val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_URL = "download_url"
    }

    override suspend fun doWork(): Result {
        val downloadUrl = inputData.getString(KEY_URL) ?: return Result.failure()

        // 检查权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return Result.failure()
        }

        // 下载 APK
        val outputFile = createOutputFile(context)
        val downloadSuccess = downloadApk(downloadUrl, outputFile)

        if (downloadSuccess) {
            // 安装 APK
            installApk(outputFile)
            return Result.success()
        } else {
            return Result.failure()
        }
    }

    private fun downloadApk(downloadUrl: String, outputFile: File): Boolean {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(downloadUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // Check for valid response code
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return false
            }

            // Download the file
            input = connection.inputStream
            output = FileOutputStream(outputFile)

            val data = ByteArray(4096)
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                output.write(data, 0, count)
            }
            return true
        } catch (e: Exception) {
            return false
        } finally {
            try {
                input?.close()
                output?.close()
            } catch (ignored: IOException) {

            }
            connection?.disconnect()
        }
    }

    private fun createOutputFile(context: Context): File {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 使用 MediaStore API (Android 10 及更高版本)
            val contentResolver = context.contentResolver
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "app_update.apk")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.android.package-archive")
            }
            val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            File(uri.toString())
        } else {
            // 使用传统的方式 (Android 9 及更低版本)
            val externalFilesDir = context.getExternalFilesDir(null)
            File(externalFilesDir, "app_update.apk")
        }
    }

    private fun installApk(apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            /**
             * 目前build.gradle.kts 文件中，minSdk 被设定为 24。而 Build.VERSION.SDK_INT 代表的是运行设备的 API 等级，它永远不会低于 minSdk。
             * FileProvider.getUriForFile API 从 Android 7.0 (API Level 24) 开始就可用，
             * TODO 建议这个判断条件可以先留着，避免在当前项目用于不同产品时，由于 minSdk< 24 时，而出错。
             */
            val apkUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(applicationContext, applicationContext.packageName + ".provider", apkFile)
            } else {
                Uri.fromFile(apkFile)
            }
            setDataAndType(apkUri, "application/vnd.android.package-archive")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        applicationContext.startActivity(intent)
    }
}