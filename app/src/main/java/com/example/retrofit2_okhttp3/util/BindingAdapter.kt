package com.example.retrofit2_okhttp3.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("glide")
    fun setGlide(view : ImageView, uri : String){
        Glide.with(view.context)
            .load(uri)
            .circleCrop()
            .into(view)
    }
}