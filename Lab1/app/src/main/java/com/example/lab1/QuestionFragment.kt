package com.example.lab1

import android.app.Activity
import android.graphics.Color
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class QuestionFragment : QuizFragment() {
    override val resourceIdentifier: Int = R.layout.list
    override val layoutIdentifier: Int = R.id.liner
    override val questionIdentifier: Int = R.array.options
}