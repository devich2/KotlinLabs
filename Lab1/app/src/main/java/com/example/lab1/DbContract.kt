package com.example.lab1

import android.provider.BaseColumns

object DbContract
{
    class Quiz: BaseColumns {
        companion object {
            const val TABLE_NAME = "results"
            const val COLUMN_USER_ID = "_id"
            const val COLUMN_NAME = "_name"
            const val COLUMN_QUESTION = "_question"
            const val COLUMN_ANSWER = "_answer"
        }
    }
}
