package net.jkcats.simplemvvm.simple.upgrade

import android.widget.Button
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import net.jkcats.simplemvvm.R
import net.jkcats.simplemvvm.basics.StandardActivity
import net.jkcats.simplemvvm.simple.task.DownloadWorker
import net.jkcats.simplemvvm.simple.task.DownloadWorker.Companion.KEY_URL

class UpgradeActivity : StandardActivity<UpgradeModel>() {

    private lateinit var upgradeBt: Button

    override fun setPageResID() = R.layout.activity_upgrade

    override fun initViews() {
        upgradeBt = findViewById(R.id.upgrade_bt)
        upgradeBt.setOnClickListener {
            getNewVersion()
        }

        mViewModel?.let {
            it.updateInfoResponse.observe(this) {
                if (it.hasNewVersion) {
                    val workManager = WorkManager.getInstance(applicationContext)

                    val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
                        .setInputData(workDataOf(KEY_URL to "https://example.com/app.apk"))
                        .build()
                    workManager.enqueue(workRequest)
                } else {
                    Toast.makeText(this, "已经最新版本", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getNewVersion() {
        mViewModel?.checkNewVersion(1)
    }

}