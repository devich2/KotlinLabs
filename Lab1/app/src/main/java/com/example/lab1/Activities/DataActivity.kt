package com.example.lab1.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_data.*
import android.util.Log
import com.example.lab1.Database.QuizRepository
import com.example.lab1.Database.Result
import com.example.lab1.Database.ResultAdapter
import com.example.lab1.R


class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        addListeners()
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

    private fun truncate()
    {
        val rep = QuizRepository(this)
        rep.open()
        rep.truncate()
        rep.close()
        finish()
    }
    private fun addListeners()
    {
        delete_all.setOnClickListener { truncate() }
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(applicationContext, EditActivity::class.java)
                val item = listView.getItemAtPosition(position) as Result
                intent.putExtra("id", item.id.toLong())
                startActivity(intent)
            }
    }
}
