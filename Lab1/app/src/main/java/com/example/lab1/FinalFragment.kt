package com.example.lab1

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class FinalFragment : Fragment() {
    private lateinit var checkBoxMap: MutableList<CheckBox>
    public lateinit var btnDisplay: Button
    private lateinit var btnCancel: Button
    private lateinit var callback : OnSubmit
    private lateinit var toBack : OnBack
    private lateinit var question : TextView
    private lateinit var checkedList : ArrayList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.final_question, container, false)
        return view
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            callback = activity as OnSubmit
            toBack = activity as OnBack
        }
        catch (ex : ClassCastException) {
            throw ClassCastException("$ex must implement onSomeEventListener");
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun processBundle()
    {
        for(s in checkedList)
        {
            checkBoxMap[s].isChecked = true
        }
    }

    private fun init() {
        createLayout()
        addGlobalEventListeners()
    }

    override fun onStart() {
        super.onStart()
        if(::checkedList.isInitialized)
        {
            processBundle()
        }
    }

    private fun back()
    {
        if(::toBack.isInitialized)
        {
            toBack.Cancel(this)
        }
    }
    private fun update(text:String)
    {
        if(::callback.isInitialized)
        {
            callback.Update(text, this)
        }
    }
    private fun addGlobalEventListeners() {
        if(::btnDisplay.isInitialized)
        {
            btnDisplay.setOnClickListener {
                var text = question.text
                var noCheck = true
                var CheckedIndex = 0
                var arrayList : ArrayList<Int> = ArrayList<Int>()
                checkedList = ArrayList<Int>()

                for (cb in checkBoxMap) {
                    if (cb.isChecked) {
                        noCheck = false
                        text = text as String + "\n" + cb.text
                        checkedList.add(CheckedIndex)
                    }
                    CheckedIndex +=1
                }
                if (noCheck) {
                    val toast = Toast.makeText(
                        context, "Not all or none selected",
                        Toast.LENGTH_SHORT
                    )
                    val v = toast.view.findViewById(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    toast.setGravity(Gravity.TOP, 0, 700)
                    toast.show()
                }
                else {
                    update(text.toString())
                }
            }
        }

        btnCancel.setOnClickListener {
            back()
        }
    }

    private fun createLayout() {

        val linearLayout = view?.findViewById<LinearLayout>(R.id.final_liner);
        question = TextView(context)
        btnDisplay = Button(context)
        btnCancel = Button(context)
        checkBoxMap = mutableListOf()

        btnDisplay.text = "Check answer!"
        btnCancel.text = "Back"
        question.textSize = 15f

        val res: Array<String> = resources.getStringArray(R.array.options2)
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
        linearLayout?.addView(btnCancel)
    }
}