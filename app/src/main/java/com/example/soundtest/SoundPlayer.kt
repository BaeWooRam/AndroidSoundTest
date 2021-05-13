package com.example.soundtest

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.*
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService


/**
 * SoundPool를 이용한 간단한 소리 재생
 */
class SoundPlayer(private val context: Context) {
    private val soundPool: SoundPool
    private val mediaPlayer: MediaPlayer
    private val ringtone: Ringtone
    private val soundUri: Uri

    private var vibrator: Vibrator? = null
    private var audioManager: AudioManager? = null

    var vibrate: LongArray? = DEFAULT_VIBRATE

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

    private fun initDefaultLoadCompleteListener() {
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            when (status) {
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

    enum class PlayType {
        SoundPool, MediaPlayer, RingtoneManager
    }

    /**
     * RingerMode과 PlayType에 따른 사운드 재생한다
     */
    fun play(playType: PlayType) {
        val mode = getPhoneRingerMode()
        Log.d(TAG, "play mode = $mode, type = ${playType.name}")

        when (mode) {
            // 진동 모드
            AudioManager.RINGER_MODE_VIBRATE -> startVibrate()
            // 소리 모드
            AudioManager.RINGER_MODE_NORMAL -> startNormal(playType)
            // 무음 모드
            AudioManager.RINGER_MODE_SILENT -> {
                startVibrate()
                startNormal(playType)
            }
        }
    }

    /**
     * 진동 실행
     */
    protected fun startVibrate() {
        if (vibrator == null)
            vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(vibrate, -1)
            vibrator!!.vibrate(effect)
        } else {
            vibrator!!.vibrate(vibrate, -1)
        }
    }

    /**
     * PlayType에 따른 벨소리 실행
     */
    protected fun startNormal(playType: PlayType) {
        when (playType) {
            PlayType.MediaPlayer -> {
                val isPlaying = mediaPlayer.isPlaying

                if (!isPlaying) {
                    Log.d(TAG, "MediaPlayer isPlaying = $isPlaying, play()")
                    mediaPlayer.start()
                } else {
                    Log.d(TAG, "MediaPlayer isPlaying = $isPlaying, Restart play()")
                    mediaPlayer.stop()
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
            }

            PlayType.SoundPool -> {
                val soundId = soundPool.load(context, SOUND_ID, 1)
                Log.d(TAG, "SoundPool play() soundId = $soundId")
            }

            PlayType.RingtoneManager -> {
                val isPlaying = ringtone.isPlaying

                if (!isPlaying) {
                    Log.d(TAG, "RingtoneManager isPlaying = $isPlaying, play()")
                    ringtone.play()
                } else {
                    Log.d(TAG, "RingtoneManager isPlaying = $isPlaying, Restart play()")
                    ringtone.stop()
                    ringtone.play()
                }
            }
        }
    }

    /**
     * @return
     * - 진동 모드 : AudioManager.RINGER_MODE_VIBRATE
     * - 벨소리 모드 : AudioManager.RINGER_MODE_NORMAL
     * - 무음 모드 : AudioManager.RINGER_MODE_SILENT
     */
    protected fun getPhoneRingerMode(): Int {
        if (audioManager == null)
            audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return audioManager!!.ringerMode
    }

    companion object {
        const val TAG = "SoundPlayer"
        const val SOUND_ID = R.raw.sound
        const val STATUS_PLAY_SOUND_SUCCESS = 0
        val DEFAULT_VIBRATE = longArrayOf(0L, 1000L, 500L, 1000L)
    }
}