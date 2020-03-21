package com.example.lab1.Fragment

import com.example.lab1.R


class QuestionFragment : QuizFragment() {
    override val resourceIdentifier: Int = R.layout.first_question
    override val layoutIdentifier: Int = R.id.liner
    override val questionIdentifier: Int = R.array.options
}