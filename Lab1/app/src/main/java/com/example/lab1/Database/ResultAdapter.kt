package com.example.lab1.Database

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab1.R


class ResultAdapter(context: Context, results: ArrayList<Result>) : ArrayAdapter<Result>(context, 0, results) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val user = getItem(position) as Result

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false)

        }

        val tvName = convertView!!.findViewById(R.id.name) as TextView

        val tvQuestion = convertView!!.findViewById(R.id.question) as TextView

        val tvAnswer = convertView!!.findViewById(R.id.answer) as TextView

        tvName.text = "Name: " + user.name

        tvQuestion.text = "Question: " + user.question

        tvAnswer.text = "Answers: " + user.answer

        return convertView

    }
}