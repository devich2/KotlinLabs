package com.example.lab1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab1.Activities.DataActivity
import com.example.lab1.Database.QuizRepository
import com.example.lab1.Database.Result
import com.example.lab1.Fragment.DetailFragment
import com.example.lab1.Fragment.FinalFragment
import com.example.lab1.Fragment.INavigation
import com.example.lab1.Fragment.QuestionFragment
import com.example.lab1.Fragment.QuizFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), INavigation {
    private var confirmed: Boolean = false
    private val finalFragment = ::FinalFragment
    private val initialFragment = ::QuestionFragment
    private val detailFragment = ::DetailFragment

    override fun Cancel(frag: QuizFragment) {
        if (frag !is QuestionFragment) {
            supportFragmentManager.popBackStack()
            DeleteInfo(if(confirmed) 2 else 1)
        } else {
            frag.ShowDisplayButton(true)
        }
    }

    override fun Update(question: String, frag: QuizFragment, bundle: Bundle) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.setText(question + "\nAnswers:" + bundle[question] as String)
        if (frag !is FinalFragment) {
            var frag = finalFragment()
            frag.arguments = bundle
            replaceQuizFragment(frag)
            confirmed = false
        } else {
            frag.ShowToast("Answer saved", 0, 800, Color.BLUE)
            saveData(bundle)
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
        if(QuizRepository(this).open().getCount() == 0L)
        {
            Toast.makeText(applicationContext, "Empty storage", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val intent: Intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }
    }

    fun DeleteInfo(count: Int) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.deleteLast(count)
    }

    fun initFragments() {
        supportFragmentManager.beginTransaction().add(R.id.frame1, initialFragment()).add(R.id.frame2, detailFragment())
            .commit()
        open.setOnClickListener { openData() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
    }

}
