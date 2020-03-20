package com.example.lab1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import kotlinx.android.synthetic.main.detail.*


class MainActivity : AppCompatActivity(), INavigation {

    override fun Cancel(frag: QuizFragment) {
        if (frag !is QuestionFragment) {
            supportFragmentManager.popBackStack()
            DeleteLastInfo()
        } else {
            frag.ShowDisplayButton(true)
        }
    }

    override fun Update(s: String, frag: QuizFragment, bundle: Bundle) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.setText(s)
        if (frag !is FinalFragment) {
            val newFragment = FinalFragment()
            newFragment.arguments = bundle
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame1, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            frag.ShowToast("Answer saved", 0, 800, Color.BLUE)
            saveData(bundle)
            frag.ShowDisplayButton(false)
        }
    }

    fun saveData(bundle: Bundle) {
        val rep = QuizRepository(this)
        lateinit var result: Result
        val name = java.util.UUID.randomUUID().toString()
        rep.open()
        for (key in bundle.keySet()) {
            result = Result(name, key, bundle[key] as String)
            rep.insert(result)
        }
        rep.close()
    }

    fun openData() {
        val intent: Intent = Intent(this, DataActivity::class.java)
        startActivity(intent)
    }

    fun DeleteLastInfo() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.deleteLast()
    }

    fun init() {
        val list = QuestionFragment()
        val detail = DetailFragment()
        supportFragmentManager.beginTransaction().add(R.id.frame1, list).add(R.id.frame2, detail)
            .commit()
        open.setOnClickListener { openData() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

}
