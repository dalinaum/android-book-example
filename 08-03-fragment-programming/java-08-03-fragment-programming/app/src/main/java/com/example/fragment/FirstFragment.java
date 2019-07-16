package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FirstFragment extends Fragment {

    private OnButtonClickedListener mOnButtonClickedListener;

    public FirstFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickedListener) {
            mOnButtonClickedListener = (OnButtonClickedListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        Button helloButton = view.findViewById(R.id.hello_button);
        helloButton.setOnClickListener(v -> mOnButtonClickedListener.onButtonClicked());
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnButtonClickedListener = null;
    }

    public interface OnButtonClickedListener {
        void onButtonClicked();
    }
}
