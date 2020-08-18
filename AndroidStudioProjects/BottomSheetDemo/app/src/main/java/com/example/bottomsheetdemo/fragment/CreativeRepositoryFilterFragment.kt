package com.example.bottomsheetdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomsheetdemo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreativeRepositoryFilterFragment :BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.layout_filter_bottom_sheet_repo,container,false)
    }
}