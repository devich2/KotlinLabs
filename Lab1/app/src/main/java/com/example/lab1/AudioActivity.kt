package com.example.lab1

import android.annotation.TargetApi
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_audio.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.util.Log
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.text.Editable
import android.webkit.URLUtil


class AudioActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pauseFlag: Boolean = false
    private lateinit var soundThread: Thread
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        setupListeners()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun setupListeners() {
        play.setOnClickListener {
            runnable = Runnable { playMusic() }
            soundThread = Thread(runnable)
            soundThread.run()
        }
            pause.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    pauseFlag = true
                    play.isEnabled = true
                    pause.isEnabled = false
                    stop.isEnabled = true
                }
            }
            stop.setOnClickListener {
                if (mediaPlayer.isPlaying || pauseFlag) {
                    pauseFlag = false
                    seekBar.progress = 0
                    mediaPlayer.stop()
                    releaseMP()
                    handler.removeCallbacks(runnable)
                    play.isEnabled = true
                    pause.isEnabled = false
                    stop.isEnabled = false
                    tv_pass.text = ""
                    tv_due.text = ""
                }
            }
            save.setOnClickListener { saveUri() }
            openStorage.setOnClickListener {openFileStorage()}
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    if (b) {
                        mediaPlayer.seekTo(i * 1000)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
    }
    private fun playMusic()
{
    if(uri != null)
    {
        if (pauseFlag) {
            mediaPlayer.seekTo(mediaPlayer.currentPosition)
            mediaPlayer.start()
            pauseFlag = false
        } else {

            mediaPlayer = MediaPlayer.create(applicationContext, uri)
            mediaPlayer.setOnPreparedListener { this }
            mediaPlayer.start()
        }
        initializeSeekBar()
        play.isEnabled = false
        pause.isEnabled = true
        stop.isEnabled = true
        mediaPlayer.setOnCompletionListener {
            play.isEnabled = true
            pause.isEnabled = false
            stop.isEnabled = false
        }
    }
    else
        Toast.makeText(this,"Bro:(",Toast.LENGTH_SHORT).show()
}
    private fun saveUri(){
        val text = editText.text.toString()
        if(URLUtil.isValidUrl(text))
        {
            uri = Uri.parse(text)
            Toast.makeText(this,"Bro:)",Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this,"Bro:(",Toast.LENGTH_SHORT).show()
        editText.setText("")
    }

    private fun openFileStorage() {
        val intent = Intent()
            .setType("audio/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            uri = data?.data //The uri with the location of the file
        }
    }

    private fun releaseMP() {
        try {
            mediaPlayer.reset()
            mediaPlayer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun initializeSeekBar() {
        seekBar.max = mediaPlayer.seconds

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentSeconds

            tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            tv_due.text = "$diff sec"
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }
}

val MediaPlayer.seconds:Int
    get() {
        return this.duration / 1000
    }

val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }

