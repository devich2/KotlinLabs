package com.example.lab1.Fragment

import com.example.lab1.R

class FinalFragment : QuizFragment() {
    override val resourceIdentifier: Int = R.layout.final_question
    override val layoutIdentifier: Int = R.id.final_liner
    override val questionIdentifier: Int = R.array.options2
}