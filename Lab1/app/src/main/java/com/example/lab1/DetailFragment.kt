package com.example.lab1

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class DetailFragment : Fragment(){

    fun setText(item: String) {
        val view = view!!.findViewById(R.id.detailView) as TextView
        Log.d("FUCK", item)
        view.text = item
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup ?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail, container, false)
    }
}