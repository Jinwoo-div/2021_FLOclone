package com.example.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.layout.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var gson: Gson = Gson()
    private var song: Song = Song()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var song = Song("라일락", "아이유", 215, false, "testTitle", 1)


        fun setFragments(flag: Int) {
            val transaction = supportFragmentManager.beginTransaction()
            when(flag){
                0->{
                    transaction.add(R.id.main_frame_fl, home())
                }
                1->{
                    transaction.add(R.id.main_frame_fl, around())
                }
                2->{
                    transaction.add(R.id.main_frame_fl, search())
                }
                3->{
                    transaction.add(R.id.main_frame_fl, store())
                }
            }
            transaction.commit()
        }
        setFragments(0)

        binding.mainNavHomeBtn.setOnClickListener {
            setFragments(0)
        }
        binding.mainNavAroundBtn.setOnClickListener {
            setFragments(1)
        }
        binding.mainNavSearchBtn.setOnClickListener {
            setFragments(2)
        }
        binding.mainNavStoreBtn.setOnClickListener {
            setFragments(3)
        }

        binding.mainCurrentPlayCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            val title = binding.mainCurrentTitleTv.text
            val artist = binding.mainCurrentArtistTv.text
            //title = song.title
            //artist = song.artist
            //playTime = song.playTime
            //isPlaying = song.isPlaying
            intent.putExtra("title", title)
            intent.putExtra("artist", artist)
            intent.putExtra("music", song.music)
            intent.putExtra("currentTime", song.currentTime)
            startActivity(intent)
        }

    }

    private fun setminiplayer(song: Song) {
        binding.mainCurrentTitleTv.text = song.title
        binding.mainCurrentArtistTv.text = song.singer
        binding.mainCurrentPlaySb.progress = song.currentTime*1000/song.playTime
        if (song.isPlaying) {
            binding.mainCurrentPlayIv.setImageResource(R.drawable.btn_miniplay_mvplay)
        }
        else {
            binding.mainCurrentPlayIv.setImageResource(R.drawable.btn_miniplay_mvpause)
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song", null)

        song = if (jsonSong == null) {
            Song("first", "아이유", 215, false, "", 1)
        }
        else {
            gson.fromJson(jsonSong, Song::class.java)
        }

        setminiplayer(song)

    }
}