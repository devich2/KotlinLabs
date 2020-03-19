package com.example.lab1

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.R.attr.key
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class MainActivity : AppCompatActivity(), OnSubmit, OnBack  {

    override fun Cancel(frag : Fragment) {
      if (frag !is ListFragment)
      {
          supportFragmentManager.popBackStack()
          DeleteLastInfo()
      }
        else
      {
          frag.btnDisplay.isClickable = true
      }
    }

    fun DeleteLastInfo()
    {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        if (fragment != null) {
            fragment.deleteLast()
        }
    }

    override fun Update(s: String, frag: Fragment) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        if (fragment != null) {
            fragment.setText(s)
        }
        if (frag !is FinalFragment)
        {
            val newFragment = FinalFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame1, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        else
        {
            frag.btnDisplay.isClickable = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = ListFragment()
        val detail = DetailFragment()
        supportFragmentManager.beginTransaction().add(R.id.frame1, list).add(R.id.frame2, detail)
                .commit()
    }

}
