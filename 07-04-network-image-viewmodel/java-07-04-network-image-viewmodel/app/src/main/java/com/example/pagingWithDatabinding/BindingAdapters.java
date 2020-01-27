package com.example.pagingWithDatabinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("imgUrl")
    public static void setImgSrc(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .into(imageView);
    }
}
