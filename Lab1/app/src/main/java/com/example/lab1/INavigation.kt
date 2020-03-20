package com.example.lab1

import android.os.Bundle
import androidx.fragment.app.Fragment

interface INavigation {
    fun Cancel(frag:QuizFragment)
    fun Update(s:String, fragment: QuizFragment, bundle: Bundle)
}