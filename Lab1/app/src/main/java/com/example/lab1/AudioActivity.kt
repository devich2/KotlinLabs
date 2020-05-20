package com.example.lab1

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_audio.*


class AudioActivity : com.example.lab1.MediaPlayer() {
    private var handler: Handler = Handler()
    private var pauseFlag: Boolean = false
    override val openButton: Button
        get() = findViewById(R.id.openStorageAudio)
    override val saveButton: Button
        get() = findViewById(R.id.saveUriAudio)
    override val editText: EditText
        get() = findViewById(R.id.editTextAudio)
    override val playButton: Button
        get() = findViewById(R.id.playAudio)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        setupListeners()
    }

    private fun setupListeners() {
        playAudio.setOnClickListener {
            runnable = Runnable { playMusic() }
            MediaThread = Thread(runnable)
            MediaThread.run()
        }
        pause.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    pauseFlag = true
                    playAudio.isEnabled = true
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
                    playAudio.isEnabled = true
                    pause.isEnabled = false
                    stop.isEnabled = false
                    tv_pass.text = ""
                    tv_due.text = ""
                }
            }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        setUpAdditionListeners()
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
        playAudio.isEnabled = false
        pause.isEnabled = true
        stop.isEnabled = true
        mediaPlayer.setOnCompletionListener {
            playAudio.isEnabled = true
            pause.isEnabled = false
            stop.isEnabled = false
        }
    }
    else
        Toast.makeText(this,"Bro:(",Toast.LENGTH_SHORT).show()
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

