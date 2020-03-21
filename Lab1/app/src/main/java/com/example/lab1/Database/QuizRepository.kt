package com.example.lab1.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.database.Cursor
import android.database.DatabaseUtils


class QuizRepository {
    private lateinit var dbHelper: DatabaseHelper
    private var database: SQLiteDatabase? = null

    constructor(context: Context) {
        dbHelper = DatabaseHelper(context.applicationContext)
    }

    fun open(): QuizRepository {
        database = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }

    private fun getAllEntries(): Cursor? {
        val columns = arrayOf<String>(
            DbContract.Quiz.COLUMN_USER_ID,
            DbContract.Quiz.COLUMN_NAME,
            DbContract.Quiz.COLUMN_QUESTION,
            DbContract.Quiz.COLUMN_ANSWER
        )
        return database?.query(DbContract.Quiz.TABLE_NAME, columns, null, null, null, null, null)
    }

    fun truncate()
    {
       database?.execSQL("DELETE FROM " + DbContract.Quiz.TABLE_NAME)

    }
    fun getResults(): List<Result> {
        val results: ArrayList<Result> = ArrayList()
        val cursor = getAllEntries()
        if(cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(DbContract.Quiz.COLUMN_USER_ID))
                    val name = cursor.getString(cursor.getColumnIndex(DbContract.Quiz.COLUMN_NAME))
                    val question = cursor.getString(cursor.getColumnIndex(DbContract.Quiz.COLUMN_QUESTION))
                    val answer = cursor.getString(cursor.getColumnIndex(DbContract.Quiz.COLUMN_ANSWER))
                    results.add(Result(id, name, question, answer))
                } while (cursor.moveToNext())
                cursor.close()
            }
        }
        return results
    }

    fun getCount(): Long {
        return DatabaseUtils.queryNumEntries(database,
            DbContract.Quiz.TABLE_NAME
        )
    }

    fun getResult(id: Long): Result? {
        var result: Result? = null
        val query = String.format(
            "SELECT * FROM %s WHERE %s=?",
            DbContract.Quiz.TABLE_NAME,
            DbContract.Quiz.COLUMN_USER_ID
        )
        val cursor = database?.rawQuery(query, arrayOf(id.toString()))
        if(cursor != null)
        {
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(DbContract.Quiz.COLUMN_USER_ID))
                val name = cursor.getString(cursor.getColumnIndex(DbContract.Quiz.COLUMN_NAME))
                val question = cursor.getString(cursor.getColumnIndex(DbContract.Quiz.COLUMN_QUESTION))
                val answer = cursor.getString(cursor.getColumnIndex(DbContract.Quiz.COLUMN_ANSWER))
                result = Result(id, name, question, answer)
            }
            cursor.close()
        }
        return result
    }

    fun insert(result: Result): Long? {

        val cv = ContentValues()
        cv.put(DbContract.Quiz.COLUMN_NAME, result.name)
        cv.put(DbContract.Quiz.COLUMN_QUESTION, result.question)
        cv.put(DbContract.Quiz.COLUMN_ANSWER, result.answer)
        return database?.insert(DbContract.Quiz.TABLE_NAME, null, cv)
    }

    fun delete(resultId: Long): Long? {

        val whereClause = "${DbContract.Quiz.COLUMN_USER_ID} = ?"
        val whereArgs = arrayOf(resultId.toString())
        return database?.delete(DbContract.Quiz.TABLE_NAME, whereClause, whereArgs)?.toLong()
    }

    fun update(result: Result): Long? {
        val whereClause = "${DbContract.Quiz.COLUMN_USER_ID} = ?"
        val cv = ContentValues()
        val whereArgs = arrayOf(result.id.toString())
        cv.put(DbContract.Quiz.COLUMN_NAME, result.name)
        cv.put(DbContract.Quiz.COLUMN_QUESTION, result.question)
        cv.put(DbContract.Quiz.COLUMN_ANSWER, result.answer)
        return database?.update(DbContract.Quiz.TABLE_NAME, cv, whereClause, whereArgs)?.toLong()
    }
}