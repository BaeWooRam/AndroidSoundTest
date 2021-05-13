package com.example.soundtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var soundPlayer:SoundPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        soundPlayer =  SoundPlayer(this)

        findViewById<Button>(R.id.btnMediaPlayStartSound).setOnClickListener {
            soundPlayer?.play(SoundPlayer.PlayType.MediaPlayer)
        }

        findViewById<Button>(R.id.btnSoundPoolStartSound).setOnClickListener {
            soundPlayer?.play(SoundPlayer.PlayType.SoundPool)
        }

        findViewById<Button>(R.id.btnRingtoneManagerStartSound).setOnClickListener {
            soundPlayer?.play(SoundPlayer.PlayType.RingtoneManager)
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}