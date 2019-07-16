package com.example.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FirstFragment : Fragment() {

    private var mOnButtonClickedListener: OnButtonClickedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnButtonClickedListener) {
            mOnButtonClickedListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val helloButton = view.findViewById<Button>(R.id.hello_button)
        helloButton.setOnClickListener { v -> mOnButtonClickedListener?.onButtonClicked() }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        mOnButtonClickedListener = null
    }

    interface OnButtonClickedListener {
        fun onButtonClicked()
    }
}
