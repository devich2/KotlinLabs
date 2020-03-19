package com.example.lab1

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), ListFragment.OnSubmit  {
    override fun Update(s: String) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame2) as DetailFragment
        if (fragment != null) {
            fragment.setText(s)
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
