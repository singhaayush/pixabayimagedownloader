package com.example.bottomsheetdemo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bottomsheetdemo.fragment.CreativeRepositoryFragment
import kotlinx.android.synthetic.main.activity_creative_repository.*
import kotlinx.android.synthetic.main.fragment_creative_repository.*

//https://devofandroid.blogspot.com/2018/02/downloadsave-image-from-webview-on-long.html
class CreativeRepositoryActivity : AppCompatActivity(),CreativeRepositoryFragment.ImageClickListener  {
    private val TAG = "CreativeRepositoryActiv"
    lateinit var toolbar: Toolbar
    lateinit var homeFragment:CreativeRepositoryFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creative_repository)
        toolbar=creative_collection_toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{onBackPressed()}

         homeFragment=CreativeRepositoryFragment()
        homeFragment.setImageClickListener(this)
        homeFragment.arguments= intent.extras
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.creative_fragment,homeFragment)
            addToBackStack("WebViewFragment")
            commit()
        }


    }

    override fun onImageClicked(resultUrl: String) {
        web_view_home.apply {
            visibility = View.VISIBLE
            webViewClient= object :WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d(TAG, "onPageFinished: $url")
                    val intent=Intent()
                    intent.putExtra("resultUrl",url)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }


            }

        }

        var setting = web_view_home.settings
        setting.apply {
            domStorageEnabled = true
            setAppCacheEnabled(true)
            cacheMode = LOAD_CACHE_ELSE_NETWORK
            domStorageEnabled = true
            javaScriptEnabled = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
        }
        Log.d(TAG, "onImageClicked: $resultUrl")
        web_view_home.loadUrl(resultUrl)
    }

    override fun onBackPressed() {

        Log.d(TAG, "onBackPressed: ")
        val fragment= supportFragmentManager.findFragmentByTag("WebViewFragment")
        if(fragment is CreativeRepositoryFragment)
        {
            val goback: Boolean = web_view.canGoBack()
            if (!goback) super.onBackPressed()
        }
        super.onBackPressed()
    }
}