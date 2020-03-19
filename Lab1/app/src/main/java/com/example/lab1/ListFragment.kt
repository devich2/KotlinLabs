package com.example.lab1

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class ListFragment : Fragment() {

    private lateinit var checkBoxMap: MutableList<CheckBox>
    private lateinit var btnDisplay: Button
    private lateinit var callback : OnSubmit
    private lateinit var resultView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.list, container, false)
        return view
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            callback = activity as OnSubmit
        }
        catch (ex : ClassCastException) {
            throw ClassCastException("$ex must implement onSomeEventListener");
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    private fun init() {
        createLayout()
        addGlobalEventListeners()
    }

    private fun addGlobalEventListeners() {
        btnDisplay.setOnClickListener {
            var text = ""
            var noCheck = true
            for (cb in checkBoxMap) {
                if (cb.isChecked) {
                    noCheck = false
                    text = text + "\n" + cb.text
                }
            }
            if (noCheck) {
                text += "No checked"
            }
            callback.Update(text)
        }
    }

    private fun createLayout() {

            val linearLayout = view?.findViewById<LinearLayout>(R.id.liner);
            val question = TextView(context)
            btnDisplay = Button(context)
            resultView = TextView(context)
            checkBoxMap = mutableListOf()

            resultView.setTypeface(resultView.typeface, Typeface.BOLD)

            question.text = "Hello, what is your option?"
            btnDisplay.text = "Check answer!"
            resultView.textSize = 20f
            question.textSize = 15f

            val res: Array<String> = resources.getStringArray(R.array.options)
            question.text = res[0]

            for (i in 1 until res.size) {
                var checkBox = CheckBox(context)
                checkBox.text = res[i]
                checkBoxMap.add(checkBox)
            }

            linearLayout?.addView(question)
            for (cb in checkBoxMap) {
                linearLayout?.addView(cb)
            }
            linearLayout?.addView(btnDisplay)
            linearLayout?.addView(resultView)

    }

    interface OnSubmit
    {
        fun Update(s:String)
    }

}