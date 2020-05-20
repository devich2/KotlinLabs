package com.example.lab1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : MediaPlayer() {

    override val openButton: Button
        get() = findViewById(R.id.openStorageVideo)
    override val saveButton: Button
        get() = findViewById(R.id.saveUriVideo)
    override val editText: EditText
        get() = findViewById(R.id.editTextVideo)
    override val playButton: Button
        get() = findViewById(R.id.playVideo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView.setMediaController(MediaController(this))
        setupListeners()
    }
    private fun setupListeners() {

        playButton.setOnClickListener {
            if(uri != null) {
                videoView.setVideoURI(uri)
                videoView.start()
            }
            else
                Toast.makeText(this,"Bro:(", Toast.LENGTH_SHORT).show()
        }
        setUpAdditionListeners()
    }
}
