package com.example.soundtest

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val soundPlayer by lazy {
        SoundPlayer(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStartSound).setOnClickListener {
            soundPlayer.play()
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}