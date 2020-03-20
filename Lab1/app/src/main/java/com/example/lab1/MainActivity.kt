package com.example.lab1

import android.graphics.Color
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), INavigation {

    override fun Cancel(frag: QuizFragment) {
        if (frag !is QuestionFragment) {
            supportFragmentManager.popBackStack()
            DeleteLastInfo()
        } else {
            frag.ShowDisplayButton(true)
        }
    }

    override fun Update(s: String, frag: QuizFragment) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.setText(s)
        if (frag !is FinalFragment) {
            val newFragment = FinalFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame1, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            frag.ShowToast("Answer saved", 0, 800, Color.BLUE)
            frag.ShowDisplayButton(false)
        }
    }

    fun DeleteLastInfo() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.deleteLast()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = QuestionFragment()
        val detail = DetailFragment()
        supportFragmentManager.beginTransaction().add(R.id.frame1, list).add(R.id.frame2, detail)
            .commit()
    }

}
