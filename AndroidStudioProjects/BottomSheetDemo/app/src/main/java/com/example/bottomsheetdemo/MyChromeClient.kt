package com.example.bottomsheetdemo

import android.content.Context
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient

class MyChromeClient(context: Context) :WebChromeClient() {
    companion object{
        private const val TAG = "MyChromeClient"
    }
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        return super.onConsoleMessage(consoleMessage)
        Log.d(TAG, "onConsoleMessage: ${consoleMessage.toString()}")
        
    }

    override fun onPermissionRequest(request: PermissionRequest?) {
       // super.onPermissionRequest(request)
        request?.grant(request.resources)
        Log.d(TAG, "onPermissionRequest: ")
    }

}