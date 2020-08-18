package com.example.bottomsheetdemo.fragment

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.*
import android.webkit.WebView.HitTestResult
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.example.bottomsheetdemo.AndroidInterface
import com.example.bottomsheetdemo.MyChromeClient
import com.example.bottomsheetdemo.R
import kotlinx.android.synthetic.main.fragment_creative_repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreativeRepositoryFragment : Fragment() ,AndroidInterface.DownloadButtonClickListener{
    private  val TAG = "CreativeRepositoryFragm"
    lateinit var mListener: CreativeRepositoryFragmentListener
    lateinit var url:String
    lateinit var mListener2: ImageClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
           // mListener = context as CreativeRepositoryFragmentListener
            val bundle=arguments
            url= arguments?.getString("url").toString()
            mListener2=context as ImageClickListener
        }
        catch (e:ClassCastException)
        {
            Log.d(TAG, "onAttach: ")
        }
    }

    fun setImageClickListener(listener: ImageClickListener)
    {
        mListener2=listener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creative_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exampleBottomSheetFragment=CreativeRepositoryFilterFragment()
        filter_btn.setOnClickListener{
            exampleBottomSheetFragment.show(fragmentManager!!,"")
        }
        web_view.also {
            it.addJavascriptInterface(AndroidInterface(this),"Android")
            it.webViewClient= object :WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    loadJS(it)
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)


                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
            it.webChromeClient=MyChromeClient(view.context)
        }
        var settings=web_view.settings

        settings.also {
            it.domStorageEnabled=true
            it.setAppCacheEnabled(true)
            it.cacheMode=WebSettings.LOAD_CACHE_ELSE_NETWORK
            it.domStorageEnabled=true
            it.javaScriptEnabled=true
            it.setRenderPriority(WebSettings.RenderPriority.HIGH)
            it.savePassword=true

        }
        registerForContextMenu(web_view)
        web_view.loadUrl(url)
    }
    interface CreativeRepositoryFragmentListener{
        public fun onFilterButtonClicked() {

        }
    }

    override fun onCreateContextMenu(
        contextMenu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(contextMenu, v, menuInfo)
        val webViewHitTestResult: HitTestResult = web_view.hitTestResult

        if (webViewHitTestResult.type == WebView.HitTestResult.IMAGE_TYPE ||
            webViewHitTestResult.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){

            contextMenu.setHeaderTitle("Download Image...");
            contextMenu.setHeaderIcon(R.drawable.ic_upload);
            contextMenu.add(0, 1, 0, "Click to download")
                .setOnMenuItemClickListener {
                    val DownloadImageURL = webViewHitTestResult.extra
                    if (URLUtil.isValidUrl(DownloadImageURL)) {
                        Log.d(TAG, "onMenuItemClick: $DownloadImageURL")
                        mListener2?.onImageClicked(DownloadImageURL.toString())
                        val mRequest =
                            DownloadManager.Request(Uri.parse(DownloadImageURL))
                        mRequest.allowScanningByMediaScanner()
                        mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        val mDownloadManager =
                            activity!!.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

                        mDownloadManager!!.enqueue(mRequest)
                        Toast.makeText(
                            activity,
                            "Image Downloaded Successfully...",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {


                        Toast.makeText(
                            activity,
                            "Sorry.. Something Went Wrong...",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    false
                }


        }


}
    // links[i].setAttribute('onclick', 'Android.onButtonsClicked(links[i].href)');

    interface ImageClickListener{
        fun onImageClicked(resultUrl:String)


    }
    fun loadJS(webView:WebView){
        webView.loadUrl(
            """javascript:(function f() {
        var links = document.getElementsByClassName('dl_btn pure-button button-green button-md ua-download-button');
        for (var i = 0, n = links.length; i < n; i++) {
             
            links[i].addEventListener('click',function(){
               Android.onButtonsClicked(this.href)
            })
          
        }
      })()"""
        )
    }

    override fun onDownloadButtonClicked(url: String) {
           GlobalScope.launch (Dispatchers.Main){
           val newUrl =url.replace("?attachment","")
             mListener2.onImageClicked(newUrl)
         }
    }



}