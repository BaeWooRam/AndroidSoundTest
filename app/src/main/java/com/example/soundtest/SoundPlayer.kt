package com.example.soundtest

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log

/**
 * SoundPool를 이용한 간단한 소리 재생
 */
class SoundPlayer(private val context: Context) {
    private val soundPool: SoundPool

    init {
        soundPool = SoundPool.Builder().setAudioAttributes(getDefaultAudioAttributes()).build()
        initDefaultLoadCompleteListener()
    }

    /**
     * 기본 AudioAttributes 세팅
     */
    private fun getDefaultAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    /**
     * 기본 LoadCompleteListener 세팅
     */
    private fun initDefaultLoadCompleteListener(){
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            when(status){
                STATUS_PLAY_SOUND_SUCCESS -> {
                    Log.d(TAG, "Load Sound Success! SampleId = $sampleId")
                    soundPool.play(sampleId, 1f, 1f, 0, 0, 1f)
                }
                else -> {
                    Log.d(TAG, "Load Sound Fail")
                }
            }
        }
    }

    /**
     * soundPool.load 실행
     */
    fun play() {
        val soundId = soundPool.load(context, SOUND, 1)
        Log.d(TAG, "play() soundId = $soundId")
    }

    companion object {
        const val TAG = "SoundPlayer"
        const val SOUND = R.raw.sound
        const val STATUS_PLAY_SOUND_SUCCESS = 0
    }
}