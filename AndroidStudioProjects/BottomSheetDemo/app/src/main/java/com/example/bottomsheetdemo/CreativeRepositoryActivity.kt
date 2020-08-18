package com.example.bottomsheetdemo

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.webkit.WebSettings
import android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.bottomsheetdemo.fragment.CreativeRepositoryFilterFragment
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
            addToBackStack(null)
            commit()
        }


    }

    override fun onImageClicked(resultUrl: String) {
        supportFragmentManager.beginTransaction().apply {
            remove(homeFragment)
            commit()
        }
          web_view_home.apply {
              visibility= View.VISIBLE
              
          }
        var setting=web_view_home.settings
        setting.apply {
             domStorageEnabled=true
            setAppCacheEnabled(true)
            cacheMode= LOAD_CACHE_ELSE_NETWORK
            domStorageEnabled=true
            javaScriptEnabled=true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            savePassword=true
        }
        web_view_home.loadUrl(resultUrl)

//        val intent= Intent()
//        intent.putExtra("resultUrl",resultUrl)
//        setResult(Activity.RESULT_OK,intent)
//        finish()
    }

//    override fun onDownloadButtonClicked(url: String) {
//        var DownloadImageURL = url
//        if (url != null) {
//            DownloadImageURL=url.replace("?attachment","")
//          //  DownloadImageURL=DownloadImageURL.replace("https","http")
//        }
//        if (URLUtil.isValidUrl(DownloadImageURL)) {
//            Log.d(TAG, "onMenuItemClick: $DownloadImageURL")
//           // onImageClicked(DownloadImageURL.toString())
//            val mRequest =
//                DownloadManager.Request(Uri.parse(DownloadImageURL))
//            mRequest.allowScanningByMediaScanner()
//            mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            val mDownloadManager =
//                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//
//            mDownloadManager!!.enqueue(mRequest)
//            Toast.makeText(
//                this,
//                "Image Downloaded Successfully...",
//                Toast.LENGTH_LONG
//            ).show()
//
//           onImageClicked(DownloadImageURL)
//        } else {
//
//
//            Toast.makeText(
//                this,
//                "Sorry.. Something Went Wrong...",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//
//    }
}