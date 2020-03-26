package com.example.lab1

import android.graphics.Color
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), INavigation {

    private var confirmed: Boolean = false
    override fun Cancel(frag: QuizFragment) {
        if (frag !is QuestionFragment) {
            supportFragmentManager.popBackStack()
            DeleteInfo(if(confirmed) 2 else 1)
        } else {
            frag.ShowDisplayButton(true)
        }
    }

    override fun Update(s: String, frag: QuizFragment) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.setText(s)
        if (frag !is FinalQuestionFragment) {
            replaceQuizFragment(FinalQuestionFragment())
            confirmed = false
        } else {
            frag.ShowToast("Answer saved", 0, 800, Color.BLUE)
            confirmed = true
            frag.ShowDisplayButton(false)
        }
    }

    private fun replaceQuizFragment(fragment: Fragment) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame1, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun DeleteInfo(count: Int) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.deleteLast(count)
    }

    fun initFragments()
    {
        val list = QuestionFragment()
        val detail = DetailFragment()
        supportFragmentManager.beginTransaction().add(R.id.frame1, list).add(R.id.frame2, detail)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
    }

}
