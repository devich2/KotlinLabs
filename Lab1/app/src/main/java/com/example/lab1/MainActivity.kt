package com.example.lab1

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity

import androidx.appcompat.app.AppCompatActivity

import android.widget.*

import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var checkBoxMap: MutableList<CheckBox>
    private lateinit var btnDisplay: Button
    private lateinit var resultView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        run()
    }

    private fun run() {
        title = "Lab1 :)"
        createLayout()
        addGlobalEventListeners()
    }

    private fun addGlobalEventListeners() {
        btnDisplay.setOnClickListener {
            resultView.text = ""
            var noCheck = true
            for (cb in checkBoxMap) {
                if (cb.isChecked) {
                    noCheck = false
                    resultView.text = resultView.text as String + "\t" + cb.text
                }
            }
            if (noCheck) {
                val toast = Toast.makeText(
                    applicationContext, "Not all or none selected",
                    Toast.LENGTH_SHORT
                )
                val v = toast.view.findViewById(android.R.id.message) as TextView
                v.setTextColor(Color.RED)
                toast.setGravity(Gravity.TOP, 0, 700)
                toast.show()
            }
        }

    }


    private fun createLayout() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout);
        val question = TextView(this)
        btnDisplay = Button(this)
        resultView = TextView(this)
        checkBoxMap = mutableListOf()

        resultView.setTypeface(resultView.typeface, Typeface.BOLD)

        question.text = "Hello, what is your option?"
        btnDisplay.text = "Check answer!"
        resultView.textSize = 20f
        question.textSize = 15f

        val res: Array<String> = resources.getStringArray(R.array.options)
        question.text = res[0]

        for (i in 1 until res.size) {
            var checkBox = CheckBox(this)
            checkBox.text = res[i]
            checkBoxMap.add(checkBox)
        }

        linearLayout.addView(question)
        for (cb in checkBoxMap) {
            linearLayout.addView(cb)
        }
        linearLayout.addView(btnDisplay)
        linearLayout.addView(resultView)
    }
}
