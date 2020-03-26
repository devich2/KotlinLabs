package com.example.lab1.Fragment

import android.os.Bundle

interface Navigation {
    fun cancel(frag: QuizFragment)
    fun update(question:String, frag: QuizFragment, bundle: Bundle)
}