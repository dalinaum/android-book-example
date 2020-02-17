package com.example.viewgroup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditViewGroup extends LinearLayout {

    public EditViewGroup(Context context) {
        super(context);
        init();
    }

    public EditViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        final Context context = getContext();
        final LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setText("Hello EditViewGroup!");
        addView(textView, layoutParams);
        final ToastEditText toastEditText = new ToastEditText(context);
        addView(toastEditText, layoutParams);
        toastEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText("Length is: " + toastEditText.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
