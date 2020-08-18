package com.example.bottomsheetdemo

import android.webkit.JavascriptInterface
import com.example.bottomsheetdemo.fragment.CreativeRepositoryFragment


class AndroidInterface(context: CreativeRepositoryFragment){
    private  val TAG = "AndroidInterface"
    private var mListener:DownloadButtonClickListener = context

    companion object{
        lateinit var resultURL:String
    }


    @JavascriptInterface
    fun onButtonsClicked(link:String)
    {
        mListener.onDownloadButtonClicked(link)
    }

    interface DownloadButtonClickListener{

        fun onDownloadButtonClicked(url:String)
    }

}