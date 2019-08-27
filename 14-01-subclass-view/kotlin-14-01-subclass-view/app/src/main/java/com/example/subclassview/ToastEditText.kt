package com.example.subclassview

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast


class ToastEditText : androidx.appcompat.widget.AppCompatEditText {

    private var toast: Toast? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, "length is " + getText()?.length, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
