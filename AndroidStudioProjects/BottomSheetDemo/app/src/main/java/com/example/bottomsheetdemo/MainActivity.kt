package com.example.bottomsheetdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.bottomsheetdemo.fragment.CreativeRepositoryFragment
import com.example.bottomsheetdemo.utils.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
//https://pixabay.com/
//"https://marketingmobile.surge.sh/"
class MainActivity : AppCompatActivity() {
    companion object {
        const val URL = "https://marketingmobile.surge.sh/"
        const val FETCH_PHOTO_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleBottomSheetFragment = BottomSheetFragment()

        bottom_sheet_opener.setOnClickListener {
            exampleBottomSheetFragment.show(
                supportFragmentManager,
                "ImageAdderSheet"
            )
        }

        btn_web_view.setOnClickListener {
            val intent = Intent(this@MainActivity, CreativeRepositoryActivity::class.java)
            intent.putExtra("url", URL)
            val bundle = Bundle();
            bundle.putString("url", URL)
            startActivityForResult(intent, FETCH_PHOTO_REQUEST_CODE, bundle)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FETCH_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var url = data?.getStringExtra("resultUrl")
            if (url != null) {
                url = url.replace("?attachment", "")
            }
            Glide.with(this)
                .load(url)
                .into(iv_result_image);

        }
    }

}