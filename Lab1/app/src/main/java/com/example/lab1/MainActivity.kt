package com.example.lab1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab1.Activities.DataActivity
import com.example.lab1.Database.QuizRepository
import com.example.lab1.Database.Result
import com.example.lab1.Fragment.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Navigation {
    private var confirmed: Boolean = false
    private val initialFragment = ::QuestionFragment
    private val detailFragment = ::DetailFragment
    private val dict: HashMap<Class<*>, Class<*>> = hashMapOf()
    private lateinit var rep: QuizRepository

    override fun Cancel(frag: QuizFragment) {
        if (frag !is QuestionFragment) {
            supportFragmentManager.popBackStack()
            deleteInfo(if(confirmed) 2 else 1)
        } else {
            frag.ShowDisplayButton(true)
        }
    }

    override fun Update(question: String, frag: QuizFragment, bundle: Bundle) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.setText(question + "\nAnswers:" + bundle[question] as String)
        if (frag !is FinalFragment) {
            val newFrag = dict.getValue(frag::class.java).newInstance() as QuizFragment
            newFrag.arguments = bundle
            replaceQuizFragment(newFrag)
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

    private fun saveData(bundle: Bundle) {
        lateinit var result: Result
        val name = java.util.UUID.randomUUID().toString()
        rep.open()
        for (key in bundle.keySet()) {
            result = Result(name, key, bundle[key] as String)
            rep.insert(result)
        }
        rep.close()
    }

    private fun openData() {
        rep.open()
        if(rep.getCount() == 0L)
        {
            Toast.makeText(applicationContext, "Empty storage", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }
        rep.close()
    }

    private fun deleteInfo(count: Int) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        fragment.deleteLast(count)
    }

    private fun addListeners()
    {
        open.setOnClickListener { openData() }
    }

    private fun initOrder()
    {
        dict[QuestionFragment::class.java] = SemiFinalFragment::class.java
        dict[SemiFinalFragment::class.java] = FinalFragment::class.java
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction().add(R.id.frame1, initialFragment()).add(R.id.frame2, detailFragment())
            .commit()
    }

    private fun setUp()
    {
        initFragments()
        addListeners()
        initOrder()
        rep = QuizRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

}
