package com.example.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.TextView;

public class TextViewBindingAdapters {
    @BindingAdapter("reverseText")
    public static void setReverseText(TextView view, String toReverse) {
        String reversed = new StringBuilder(toReverse).reverse().toString();
        view.setText(reversed);
    }
}
