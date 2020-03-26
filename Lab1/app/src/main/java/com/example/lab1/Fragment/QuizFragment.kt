package com.example.lab1.Fragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

abstract class QuizFragment : Fragment() {
    private lateinit var checkBoxMap: MutableList<CheckBox>
    private lateinit var btnDisplay: Button
    private lateinit var btnCancel: Button
    private lateinit var callback: Navigation
    private lateinit var question: TextView
    private lateinit var checkedList: ArrayList<Int>
    private var bundle: Bundle = Bundle()

    abstract val resourceIdentifier: Int
    abstract val layoutIdentifier: Int
    abstract val questionIdentifier: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { bundle = it}
        return inflater.inflate(resourceIdentifier, container, false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            callback = activity as Navigation
        } catch (ex: ClassCastException) {
            throw ClassCastException("$ex must implement onSomeEventListener");
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun processBundle() {
        for (s in checkedList) {
            checkBoxMap[s].isChecked = true
        }
    }

    fun ShowDisplayButton(s: Boolean) {
        btnDisplay.isClickable = s
    }

    private fun init() {
        createLayout()
        addGlobalEventListeners()
    }

    override fun onStart() {
        super.onStart()
        if (::checkedList.isInitialized) {
            processBundle()
        }
    }

    private fun back() {
        if (::callback.isInitialized) {
            callback.Cancel(this)
        }
    }

    private fun update() {
        if (::callback.isInitialized) {
            callback.Update(question.text as String, this, bundle)
        }
    }

    private fun addGlobalEventListeners() {
        if (::btnDisplay.isInitialized) {
            btnDisplay.setOnClickListener {
                var text = ""
                var noCheck = true
                var CheckedIndex = 0
                checkedList = ArrayList<Int>()
                for (cb in checkBoxMap) {
                    if (cb.isChecked) {
                        noCheck = false
                        text = text + "\n" + cb.text
                        checkedList.add(CheckedIndex)
                    }
                    CheckedIndex += 1
                }
                if (noCheck) {
                   ShowToast("Nothing selected", 0, 800, Color.RED)
                } else {
                    bundle.putString(question.text as String, text)
                    update()
                }
            }
        }

        if (::btnCancel.isInitialized) {
            btnCancel.setOnClickListener {
                back()
            }
        }
    }

    fun ShowToast(text: String, x: Int, y: Int, color: Int)
    {
        val toast = Toast.makeText(
            context, text,
            Toast.LENGTH_SHORT
        )
        val v = toast.view.findViewById(android.R.id.message) as TextView
        v.setTextColor(color)
        toast.setGravity(Gravity.TOP, x, y)
        toast.show()
    }

    private fun createLayout() {

        val linearLayout = view?.findViewById<LinearLayout>(layoutIdentifier);
        question = TextView(context)
        btnDisplay = Button(context)
        btnCancel = Button(context)
        checkBoxMap = mutableListOf()

        btnDisplay.text = "Check answer!"
        btnCancel.text = "Back"
        question.textSize = 15f

        val res: Array<String> = resources.getStringArray(questionIdentifier)
        question.text = res[0]

        for (i in 1 until res.size) {
            val checkBox = CheckBox(context)
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