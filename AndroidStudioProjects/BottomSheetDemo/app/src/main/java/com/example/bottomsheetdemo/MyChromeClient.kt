package com.example.bottomsheetdemo

import android.Manifest
import android.content.Context
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import com.example.bottomsheetdemo.MyChromeClient.Companion.PERM_AUDIO
import com.example.bottomsheetdemo.fragment.CreativeRepositoryFragment
import pub.devrel.easypermissions.EasyPermissions

class MyChromeClient(var context: CreativeRepositoryFragment) :WebChromeClient() {
    lateinit var mListener:ImageSelectedListener
    init {
        mListener=context as CreativeRepositoryFragment
    }
    companion object{
        private const val TAG = "MyChromeClient"
        private val PERM_CAMERA =
            arrayOf<String>(Manifest.permission.CAMERA)
        private val PERM_AUDIO= arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAPTURE_AUDIO_OUTPUT,Manifest.permission.MODIFY_AUDIO_SETTINGS)
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_AUDIO_PERMISSION=2
    }



    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
        super.onConsoleMessage(message, lineNumber, sourceID)
        Log.d(TAG, "onConsoleMessage: $message")
        if(message!=null&&message.contains("I am the selected Image [object Object]"))
        {
            mListener.onImageSelected()
        }
    }



    interface ImageSelectedListener{
        fun onImageSelected()
    }

}