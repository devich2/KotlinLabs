package com.example.lab1.Fragment

import android.graphics.Color
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.lab1.R


class DetailFragment : Fragment() {

    fun setText(item: String) {
        val textView = TextView(context)
        val linearLayout = view?.findViewById<LinearLayout>(R.id.detail_liner);
        textView.text = item
        val v = View(context)
        v.layoutParams = LinearLayout.LayoutParams(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            6
        )
        v.setBackgroundColor(Color.YELLOW)

        linearLayout?.addView(textView)
        linearLayout?.addView(v)
    }

    fun deleteLast() {
        val list: MutableList<TextView> = mutableListOf()
        val linearLayout = view?.findViewById<LinearLayout>(R.id.detail_liner);
        for (i in 0 until linearLayout!!.childCount)
            if (linearLayout.getChildAt(i) is TextView)
                list.add(linearLayout.getChildAt(i) as TextView)

        linearLayout.removeView(list[list.size - 1])
        linearLayout.removeViewAt(linearLayout.childCount - 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail, container, false)
    }
}