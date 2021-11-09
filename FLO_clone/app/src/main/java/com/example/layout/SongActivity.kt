package com.example.layout

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.layout.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity: AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    private lateinit var player: Player

    //private val handler = Handler(Looper.getMainLooper())
    private var repeatFlag: Boolean = false
    private var shuffleFlag: Boolean = false
    private var playingFlag: Boolean = false
    private var mediaPlayer: MediaPlayer? = null
    private var gson:Gson = Gson()
    private lateinit var song: Song
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val artist: String = intent.getStringExtra("artist").toString()
        val title: String = intent.getStringExtra("title").toString()
        val playTime = 100
        val isPlaying = false
        val currentPlay = 0
        val music = "music_night"
        val testMusic = resources.getIdentifier(music, "raw", this.packageName)

        playingFlag = isPlaying
        if (playingFlag) {
            binding.songControlPlayBtn.setImageResource(R.drawable.nugu_btn_pause_32)
        }
        song = setInit(artist, title, playTime, isPlaying)

        player = Player(song.playTime, song.isPlaying)
        player.start()

        mediaPlayer = MediaPlayer.create(this, testMusic)


        binding.songControlRepeatBtn.setOnClickListener {
            setRepeat()
        }

        binding.songControlShuffleBtn.setOnClickListener {
            setShuffle()
        }

        binding.songSetGobackBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.songControlPlayBtn.setOnClickListener {
            setPlay()
        }
    }

    public fun setPlay() {
        when (playingFlag) {
            (true) -> {
                binding.songControlPlayBtn.setImageResource(R.drawable.btn_player_play)
                playingFlag = false
                mediaPlayer?.pause()

            }
            (false) -> {
                binding.songControlPlayBtn.setImageResource(R.drawable.nugu_btn_pause_32)
                playingFlag = true
                mediaPlayer?.start()
            }
        }
    }

    private fun setShuffle() {
        when (shuffleFlag) {
            (true) -> {
                binding.songControlShuffleBtn.setImageResource(R.drawable.btn_playlist_random_on)
                shuffleFlag = false
            }
            (false) -> {
                binding.songControlShuffleBtn.setImageResource(R.drawable.nugu_btn_random_inactive)
                shuffleFlag = true
            }
        }

    }

    private fun setRepeat() {
        when (repeatFlag) {
            (true) -> {
                binding.songControlRepeatBtn.setImageResource(R.drawable.nugu_btn_repeat_inactive)
                repeatFlag = false
            }
            (false) -> {
                binding.songControlRepeatBtn.setImageResource(R.drawable.btn_playlist_repeat_on)
                repeatFlag = true
            }
        }
    }

    private fun setInit(artist: String, title: String, playTime: Int, isPlaying: Boolean): Song {
        var song = Song(artist, title, playTime, isPlaying)
        binding.songInfoTitleTv.text = song.title
        binding.songInfoArtistTv.text = song.singer
        binding.songInfoTotaltimeTv.text = song.playTime.toString()
        playingFlag = song.isPlaying
        return song
    }

    inner class Player(private val playTime: Int, var isPlaying: Boolean) : Thread() {
        private var second = 0
        override fun run() {
            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }
                    if (playingFlag) {
                        sleep(1000)
                        second++
                        runOnUiThread {
                            binding.songInfoPlayingtimeTv.text =
                                String.format("%02d:%02d", second / 60, second % 60)
                            binding.songInfoSeekSb.progress = second * 1000 / playTime

                        }
//                handler.post {
//                    binding.songInfoPlayingtimeTv.text = String.format("%02d:%02d", second/60, second%60)
//                }
                    }
                }

            } catch (e: InterruptedException) {
                Log.d("interrupt", "쓰레드 종료")
            }


        }

    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        playingFlag = false
        song.currentTime = (binding.songInfoSeekSb.progress*(song.playTime))/1000
        setPlay()

        song.title = "exit"
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val json = gson.toJson(song)
        editor.putString("song", json)

        editor.apply()


    }
    override fun onDestroy() {
        player.interrupt()
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}