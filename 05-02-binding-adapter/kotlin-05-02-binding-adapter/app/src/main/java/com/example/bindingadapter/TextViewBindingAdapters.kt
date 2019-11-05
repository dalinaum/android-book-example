package com.example.bindingadapter

import androidx.databinding.BindingAdapter
import android.widget.TextView

object TextViewBindingAdapters {
    @BindingAdapter("reverseText")
    fun setReverseText(view: TextView, toReverse: String) {
        val reversed = StringBuilder(toReverse).reverse().toString()
        view.text = reversed
    }
}
