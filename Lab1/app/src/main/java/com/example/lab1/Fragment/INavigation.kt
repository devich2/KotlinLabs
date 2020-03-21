package com.example.lab1.Fragment

import android.os.Bundle

interface INavigation {
    fun Cancel(frag: QuizFragment)
    fun Update(question:String, frag: QuizFragment, bundle: Bundle)
}