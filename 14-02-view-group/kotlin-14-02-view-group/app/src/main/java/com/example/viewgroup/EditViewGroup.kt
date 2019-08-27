package com.example.viewgroup

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

class EditViewGroup : LinearLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
        val context = context
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val textView = TextView(context)
        textView.text = "Hello EditViewGroup!"
        addView(textView, layoutParams)
        val toastEditText = ToastEditText(context)
        addView(toastEditText, layoutParams)
        toastEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textView.text = "Length is: " + toastEditText.text!!.length
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}
