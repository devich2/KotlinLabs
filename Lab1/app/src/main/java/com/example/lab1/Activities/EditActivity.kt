package com.example.lab1.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit.*
import android.widget.Button
import com.example.lab1.Database.QuizRepository
import com.example.lab1.Database.Result
import com.example.lab1.R


class EditActivity : AppCompatActivity() {

    var resultId: Long = 0
    var optionList: MutableList<EditText> = mutableListOf()

    fun addEventListeners() {
        val buttonUpdate = Button(this)
        val buttonDelete = Button(this)
        buttonUpdate.text = "Update"
        buttonDelete.text = "Delete"
        buttonUpdate.setOnClickListener { update() }
        buttonDelete.setOnClickListener { delete() }
        edit_liner.addView(buttonUpdate)
        edit_liner.addView(buttonDelete)
    }

    private fun getOptionList(): String {
        var text = ""
        for (ed in optionList) {
            text = text + "\n" + ed.text.toString()
        }
        return text
    }

    fun update() {
        val rep = QuizRepository(this).open()

        rep.update(
            Result(
                resultId.toInt(),
                editName.text.toString(),
                editQuestion.text.toString(),
                getOptionList()
            )
        )
        rep.close()
        finish()
    }

    fun delete() {
        val rep = QuizRepository(this).open()
        rep.delete(resultId)
        rep.close()
        finish()
    }

    fun createLayout() {
        val rep = QuizRepository(this)
        rep.open()
        val result = rep.getResult(resultId)
        if (result != null) {
            editName.setText(result.name)
            editQuestion.setText(result.question)
            val delimiter = "\n"
            val stringList = result.answer.split(delimiter)
            for (l in stringList) {
                if (l.isNotEmpty()) {
                    val editText = EditText(this)
                    editText.setText(l)
                    edit_liner.addView(editText)
                    optionList.add(editText)
                }
            }
        }
        rep.close()
    }

    fun init() {
        createLayout()
        addEventListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val extras = intent.extras
        if (extras != null) {
            resultId = extras.getLong("id")
        }
        init()
    }
}
