package com.example.lab1.Fragment

import android.os.Bundle

interface Navigation {
    fun Cancel(frag: QuizFragment)
    fun Update(question:String, frag: QuizFragment, bundle: Bundle)
}