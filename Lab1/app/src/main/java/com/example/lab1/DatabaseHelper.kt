package com.example.lab1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract.Helpers.update
import android.content.ContentValues
import java.nio.file.Files.delete



class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "quiz.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DbContract.Quiz.TABLE_NAME} (" +
                    "${DbContract.Quiz.COLUMN_USER_ID} INTEGER PRIMARY KEY," +
                    "${DbContract.Quiz.COLUMN_NAME} TEXT," +
                    "${DbContract.Quiz.COLUMN_QUESTION} TEXT," +
                    "${DbContract.Quiz.COLUMN_ANSWER} TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DbContract.Quiz.TABLE_NAME}"
    }

}