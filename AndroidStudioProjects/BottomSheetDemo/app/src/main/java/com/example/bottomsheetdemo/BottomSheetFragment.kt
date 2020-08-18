package com.example.bottomsheetdemo
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import com.example.bottomsheetdemo.utils.Toast

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.layout_bottom_sheet,container,false)
        return view

    }
    interface BottomSheetListener{

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        option1_layout.setOnClickListener { Toast.makeText(view.context,"Select from repo ",Toast.LENGTH_SHORT).show()
            val intent:Intent= Intent(view.context,CreativeRepositoryActivity::class.java)
            this.dismiss()
        startActivity(intent)}
        option2_layout.setOnClickListener { Toast.makeText(view.context,"Select from gallery ",Toast.LENGTH_SHORT).show() }
        cancel_btn_bottom_sheet.setOnClickListener { this.dismiss() }
    }

}