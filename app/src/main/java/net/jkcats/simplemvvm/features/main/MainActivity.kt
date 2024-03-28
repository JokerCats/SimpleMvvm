package net.jkcats.simplemvvm.features.main

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.nuwarobotics.lib.visual.model.Recognition
import com.nuwarobotics.service.camera.common.Constants
import com.nuwarobotics.service.camera.sdk.CameraSDK
import com.nuwarobotics.service.camera.sdk.OutputData
import net.jkcats.simplemvvm.R
import net.jkcats.simplemvvm.basics.StandardActivity
import net.jkcats.simplemvvm.features.face.FaceControlExampleActivity
import net.jkcats.simplemvvm.features.main.data.FRData
import java.io.File
import java.io.IOException


class MainActivity : StandardActivity<MainModel>() {


    private lateinit var path: String
    private val TAG = "MainActivity"
    private val cameraSDK = CameraSDK(this)
    private lateinit var mOutputTextView: TextView
    private lateinit var imagePath: TextView
    private lateinit var photo_iv: ImageView
    private lateinit var mGson: Gson


    override fun setPageResID() = R.layout.activity_main

    override fun initViews() {
        val bindBtn = findViewById<Button>(R.id.bindBtn)
        val unbindBtn = findViewById<Button>(R.id.unbindBtn)
        imagePath = findViewById<TextView>(R.id.imagePath)
        photo_iv = findViewById<ImageView>(R.id.photo_iv)

        mOutputTextView = findViewById(R.id.outputTextView)
        bindBtn.setOnClickListener(mOnClickListener)
        unbindBtn.setOnClickListener(mOnClickListener)

        val intent = Intent()
        intent.setClassName(Constants.SERVICE_PACKAGENAME, Constants.SERVICE_CLASSNAME)
        startService(intent)

        mGson = Gson()
        findViewById<Button>(R.id.takePhoto).setOnClickListener(mOnClickListener)
        findViewById<Button>(R.id.faceControl).setOnClickListener(mOnClickListener)
    }

    private val mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.bindBtn -> {
                Log.d(TAG, "bindBtn")
                //register get which recognition data
                cameraSDK.register(
                    mCameraSDKCallback,
                    Constants.FACE_DETECTION or Constants.FACE_RECOGNITION or Constants.OBJ_RECOGNITION or Constants.FACE_TRACK,
                    this@MainActivity.packageName
                )
            }

            R.id.unbindBtn -> {
                Log.d(TAG, "unbindBtn")
                cameraSDK.unregister(this@MainActivity.packageName)
            }

            R.id.takePhoto -> {
                cameraSDK.takePicture()
            }

            R.id.faceControl -> {
                startActivity(Intent(this, FaceControlExampleActivity::class.java))
            }

            else -> {

            }

        }
    }

    private val mCameraSDKCallback = object : CameraSDK.CameraSDKCallback {
        override fun onConnected(isConnected: Boolean) {
            Log.d(TAG, "onConnected:$isConnected")
        }

        override fun onOutput(resultMap: Map<Int, OutputData>) {
            Log.d(TAG, "onOutput:$resultMap")
            val sb = StringBuilder()
            for ((type, outputData) in resultMap) {
                if (outputData != null && outputData.data != null && outputData.data != "null") {
                    Log.d(TAG, "onOutput:$type: ${outputData.processTime} ${outputData.data}")
                    sb.append("$type: ${outputData.processTime} ${outputData.data}\n")
                    when (type) {
                        Constants.FACE_DETECTION -> {
                            val sMapper = ObjectMapper()
                            var list: List<Rect>? = null
                            try {
                                list = sMapper.readValue(outputData.data, object : TypeReference<List<Rect>>() {})
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            if (list != null && list.isNotEmpty()) {
                                for (rect in list) {
                                    if (rect != null) Log.d(TAG, "Got face rect:(${rect.bottom},${rect.left},${rect.right},${rect.top})")
                                }
                            }
                        }

                        Constants.FACE_RECOGNITION -> {
                            val returnFace = mGson.fromJson(outputData.data, FRData::class.java)
                            if (returnFace != null && returnFace.name != "@#$" && returnFace.name != "null") {
                                Log.d(TAG, "Find FaceData user_name:${returnFace.name}")  //return name come from registered on Robot Family.
                                Log.d(TAG, "user face rect : ${returnFace.rect.toString()}")
                                Log.d(TAG, "user wear mask : ${returnFace.mask}")
                                Log.d(TAG, "user age : ${returnFace.age}")
                            } else {
                                if (returnFace != null) Log.d(TAG, "Unknown stranger ${returnFace.name}")
                            }
                        }

                        Constants.FACE_TRACK -> {
                        }

                        Constants.OBJ_RECOGNITION -> {
                            val mConfidence = 0.5f //standard of confidence
                            val sMapper = ObjectMapper()
                            var list: List<Recognition>? = null
                            try {
                                list = sMapper.readValue(outputData.data, object : TypeReference<List<Recognition>>() {})
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            Log.d(TAG, "onOutput.list = $list")
                            if (list != null && list.isNotEmpty()) {
                                var title: String? = null
                                var confidence = 0.0
                                //Find best confidence result.
                                for (recognition in list) {
                                    Log.d(TAG, "onOutput:${recognition.title}, confidence = ${recognition.confidence}")
                                    if (recognition.confidence > confidence) {
                                        title = recognition.title
                                        confidence = recognition.confidence
                                    }
                                }
                                if (confidence > mConfidence) {
                                    val bundle = Bundle()
                                    bundle.putString("title", title)
                                    bundle.putDouble("confidence", confidence)
                                    bundle.putString("frameId", outputData.frameId)
                                }
                            }
                        }
                    }
                }
            }
            val info = sb.toString()
            runOnUiThread {
                mOutputTextView.text = info
            }
        }

        override fun onPictureTaken(path: String) {
            Log.v(TAG, "image path=$path")
            imagePath.text = "image path= $path"

            if (path.isBlank()) {
                return
            }
            this@MainActivity.path = path
            val builder = AlertDialog.Builder(this@MainActivity).apply {
                setTitle("提示")
                setMessage("预览照片")
                setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                setPositiveButton("确定") { dialog, _ ->
                    requestStoragePermission()
                }
            }
            builder.create().show()
        }
    }


    override fun onPause() {
        cameraSDK.unregister(packageName)
        super.onPause()
    }


    private val REQUEST_CODE_STORAGE_PERMISSION = 100

    // 在需要访问存储的地方调用此方法
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        } else {
            // 如果已经有权限，可以执行访问存储的操作
            loadAndDisplayImage()
        }
    }

    // 处理权限请求结果
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了存储权限，可以执行访问存储的操作
                loadAndDisplayImage()
            } else {
                Toast.makeText(this, "需要存储权限来加载图片", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 加载并显示图片的方法
    private fun loadAndDisplayImage() {
        if (path.isBlank()) {
            return
        }
//        val imagePath = "/storage/emulated/0/Pictures/BellaCameraApp/20240328-163217.jpg"
        Glide.with(this)
            .load(File(path))
            .into(photo_iv)
    }


}