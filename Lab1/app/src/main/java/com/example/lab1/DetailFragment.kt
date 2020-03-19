package com.example.lab1

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import android.widget.EditText



class DetailFragment : Fragment(){

    fun setText(item: String) {
        val textView = TextView(context)
        val linearLayout = view?.findViewById<LinearLayout>(R.id.detail_liner);
        textView.text = item
        linearLayout?.addView(textView)
    }

    fun deleteLast()
    {
        val list : MutableList<TextView> = mutableListOf()
        val linearLayout = view?.findViewById<LinearLayout>(R.id.detail_liner);
        for (i in 0 until linearLayout!!.getChildCount())
            if (linearLayout.getChildAt(i) is TextView)
                list.add(linearLayout.getChildAt(i) as TextView)

        linearLayout.removeView(list[list.size - 1])
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup ?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail, container, false)
    }
}