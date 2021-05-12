package com.example.soundtest

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var soundPlayer:SoundPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        soundPlayer =  SoundPlayer(this)

        findViewById<Button>(R.id.btnMediaPlayStartSound).setOnClickListener {
            soundPlayer?.play(SoundPlayer.Type.MediaPlayer)
        }

        findViewById<Button>(R.id.btnSoundPoolStartSound).setOnClickListener {
            soundPlayer?.play(SoundPlayer.Type.SoundPool)
        }

        findViewById<Button>(R.id.btnRingtoneManagerStartSound).setOnClickListener {
            soundPlayer?.play(SoundPlayer.Type.RingtoneManager)
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}