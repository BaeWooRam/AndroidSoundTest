package com.example.soundtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var soundPlayer:SoundPlayer? = null
    private var isStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnMediaPlayStartSound).setOnClickListener {
            Log.d(TAG, "btnMediaPlayStartSound click isStart = $isStart")
            isStart = if(!isStart) {
                soundPlayer?.play(SoundPlayer.PlayType.MediaPlayer)
                true
            }else{
                soundPlayer?.stop(SoundPlayer.PlayType.MediaPlayer)
                false
            }

        }

        findViewById<Button>(R.id.btnSoundPoolStartSound).setOnClickListener {
            Log.d(TAG, "btnSoundPoolStartSound click isStart = $isStart")
            isStart = if(!isStart) {
                soundPlayer?.play(SoundPlayer.PlayType.SoundPool)
                true
            }else{
                soundPlayer?.stop(SoundPlayer.PlayType.SoundPool)
                false
            }
        }

        findViewById<Button>(R.id.btnRingtoneManagerStartSound).setOnClickListener {
            Log.d(TAG, "btnRingtoneManagerStartSound click isStart = $isStart")
            isStart = if(!isStart) {
                soundPlayer?.play(SoundPlayer.PlayType.RingtoneManager)
                true
            }else{
                soundPlayer?.stop(SoundPlayer.PlayType.RingtoneManager)
                false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart : SoundPlayer init()")
        soundPlayer =  SoundPlayer(this)
        soundPlayer?.init()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop : SoundPlayer clear()")
        soundPlayer?.clear()
    }

    companion object{
        const val TAG = "MainActivity"
    }
}