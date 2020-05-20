package com.example.lab1

import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_audio.*

abstract class MediaPlayer: AppCompatActivity() {
    protected lateinit var mediaPlayer: MediaPlayer
    protected lateinit var runnable: Runnable
    protected lateinit var MediaThread: Thread
    protected var uri: Uri? = null
    abstract val saveButton: Button
    abstract val openButton: Button
    abstract val editText: EditText
    abstract val playButton: Button

    private fun saveUri(){
        val text = editText.text.toString()
        if(URLUtil.isValidUrl(text))
        {
            uri = Uri.parse(text)
            Toast.makeText(this,"Bro:)", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this,"Bro:(", Toast.LENGTH_SHORT).show()
        editText.setText("")
    }
    private fun openFileStorage() {
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            uri = data?.data //The uri with the location of the file
        }
    }

    protected fun releaseMP() {
        try {
            mediaPlayer.reset()
            mediaPlayer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun setUpAdditionListeners(){
        saveButton.setOnClickListener{ saveUri() }
        openButton.setOnClickListener {openFileStorage()}
    }
}