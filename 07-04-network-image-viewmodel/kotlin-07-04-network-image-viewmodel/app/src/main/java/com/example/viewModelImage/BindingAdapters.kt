package com.example.pagingWithDatabinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setImgSrc(imageView: ImageView, imgUrl: String?) {
        Glide.with(imageView.context)
                .load(imgUrl)
                .into(imageView)
    }
}