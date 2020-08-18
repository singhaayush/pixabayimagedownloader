package com.example.bottomsheetdemo.utils

import android.content.Context

fun Context.Toast(message:String){
    android.widget.Toast.makeText(this,message, android.widget.Toast.LENGTH_SHORT).show()
}