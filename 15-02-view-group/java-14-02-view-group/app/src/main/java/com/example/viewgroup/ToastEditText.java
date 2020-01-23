package com.example.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;


public class ToastEditText extends androidx.appcompat.widget.AppCompatEditText {

    private Toast toast;

    public ToastEditText(Context context) {
        super(context);
    }

    public ToastEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToastEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), "length is " + getText().length(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
