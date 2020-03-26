package com.example.lab1.Activities

import android.content.Intent
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

    private fun getOptionList(): String {
        var text = ""
        for (ed in optionList) {
            text = text + "\n" + ed.text.toString()
        }
        return text
    }

    private fun update() {
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
        goHome("Record edited...")
    }

    private fun delete() {
        val rep = QuizRepository(this).open()
        rep.delete(resultId)
        rep.close()
        goHome("Record deleted...")
    }

    private fun goHome(message: String)
    {
        val output = Intent();
        output.putExtra("message", message);
        setResult(RESULT_OK, output);
        finish();
    }

    private fun createLayout() {
        val rep = QuizRepository(this)
        rep.open()
        val result = rep.getResult(resultId)
        result?.let {
            editName.setText(it.name)
            editQuestion.setText(it.question)
            val delimiter = "\n"
            val stringList = it.answer.split(delimiter)
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

    private fun addEventListeners() {
        val buttonUpdate = Button(this)
        val buttonDelete = Button(this)
        buttonUpdate.text = "Update"
        buttonDelete.text = "Delete"
        buttonUpdate.setOnClickListener { update() }
        buttonDelete.setOnClickListener { delete() }
        edit_liner.addView(buttonUpdate)
        edit_liner.addView(buttonDelete)
    }

    private fun init() {
        createLayout()
        addEventListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        intent.extras?.let {
            resultId = it.getLong("id")
        }
        init()
    }
}
