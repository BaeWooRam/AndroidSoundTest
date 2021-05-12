package com.example.soundtest

import android.content.Context
import android.media.*
import android.net.Uri
import android.util.Log

/**
 * SoundPool를 이용한 간단한 소리 재생
 */
class SoundPlayer(private val context: Context) {
    enum class Type{
        SoundPool, MediaPlayer, RingtoneManager
    }

    private val soundPool: SoundPool
    private val mediaPlayer:MediaPlayer
    private val ringtone:Ringtone

    private val soundUri:Uri

    init {
        //SoundPool
        soundPool = SoundPool.Builder().setAudioAttributes(getDefaultAudioAttributes()).build()
        initDefaultLoadCompleteListener()

        //MediaPlayer
        mediaPlayer = MediaPlayer.create(context, SOUND_ID)

        //RingtoneManager
        val packageName = context.packageName
        val uriPath = "android.resource://$packageName/$SOUND_ID"
        Log.d(TAG, "UriPath = $uriPath")
        soundUri = Uri.parse(uriPath)
        ringtone = RingtoneManager.getRingtone(context, soundUri)
    }


    private fun getDefaultAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

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
    fun play(type:Type) {
        when(type){
            Type.MediaPlayer -> {
                val isPlaying = mediaPlayer.isPlaying

                if(!isPlaying) {
                    Log.d(TAG, "MediaPlayer isPlaying = $isPlaying, play()")
                    mediaPlayer.start()
                }else {
                    Log.d(TAG, "MediaPlayer isPlaying = $isPlaying, Restart play()")
                    mediaPlayer.stop()
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
            }

            Type.SoundPool -> {
                val soundId = soundPool.load(context, SOUND_ID, 1)
                Log.d(TAG, "SoundPool play() soundId = $soundId")
            }

            Type.RingtoneManager -> {
                val isPlaying = ringtone.isPlaying

                if(!isPlaying){
                    Log.d(TAG, "RingtoneManager isPlaying = $isPlaying, play()")
                    ringtone.play()
                }else{
                    Log.d(TAG, "RingtoneManager isPlaying = $isPlaying, Restart play()")
                    ringtone.stop()
                    ringtone.play()
                }
            }
        }
    }

    companion object {
        const val TAG = "SoundPlayer"
        const val SOUND_ID = R.raw.sound
        const val STATUS_PLAY_SOUND_SUCCESS = 0
    }
}