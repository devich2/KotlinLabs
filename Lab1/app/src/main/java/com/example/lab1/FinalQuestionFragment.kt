package com.example.lab1

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class FinalQuestionFragment : QuizFragment() {
    override val resourceIdentifier: Int = R.layout.final_question
    override val layoutIdentifier: Int = R.id.final_liner
    override val questionIdentifier: Int = R.array.options2
}