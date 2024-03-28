package net.jkcats.simplemvvm.features.main

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import java.io.IOException

class MainActivity : StandardActivity<MainModel>() {


    private val TAG = "MainActivity"
    private val cameraSDK = CameraSDK(this)
    private lateinit var mOutputTextView: TextView
    private lateinit var mGson: Gson


    override fun setPageResID() = R.layout.activity_main

    override fun initViews() {
        val bindBtn = findViewById<Button>(R.id.bindBtn)
        val unbindBtn = findViewById<Button>(R.id.unbindBtn)

        mOutputTextView = findViewById(R.id.outputTextView)
        bindBtn.setOnClickListener(mOnClickListener)
        unbindBtn.setOnClickListener(mOnClickListener)

        val intent = Intent()
        intent.setClassName(Constants.SERVICE_PACKAGENAME, Constants.SERVICE_CLASSNAME)
        startService(intent)

        mGson = Gson()
        findViewById<Button>(R.id.takePhoto).setOnClickListener(mOnClickListener)
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
                startActivity(Intent(this, FaceControlExampleActivity::class.java))
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
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        cameraSDK.unregister(packageName)
        super.onPause()
    }


}