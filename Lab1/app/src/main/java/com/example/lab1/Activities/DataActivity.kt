package com.example.lab1.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Message
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_data.*
import android.util.Log
import android.widget.Toast
import com.example.lab1.Database.QuizRepository
import com.example.lab1.Database.Result
import com.example.lab1.Database.ResultAdapter
import com.example.lab1.R


class DataActivity : AppCompatActivity() {
    private lateinit var rep: QuizRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        setUp()
    }

    override fun onResume() {
        super.onResume()
        val rep = QuizRepository(this)
        rep.open()
        val users = rep.getResults()
        val resultAdapter = ResultAdapter(this, ArrayList(users))
        listView.adapter = resultAdapter
        rep.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(applicationContext, data?.getStringExtra("message"), Toast.LENGTH_SHORT).show()
    }

    fun startEdit(id: Int)
    {
        val intent = Intent(applicationContext, EditActivity::class.java)
        intent.putExtra("id", id.toLong())
        startActivityForResult(intent, 10)
    }

    private fun setUp()
    {
        init()
        addListeners()
    }

    private fun init()
    {
        rep = QuizRepository(this)
    }

    private fun truncate()
    {
        rep.open()
        rep.truncate()
        rep.close()
        goHome("Database cleared")
    }

    private fun goHome(message: String)
    {
        val output = Intent();
        output.putExtra("message", message);
        setResult(RESULT_OK, output);
        finish();
    }

    private fun addListeners()
    {
        delete_all.setOnClickListener { truncate() }
    }
}
