package com.example.layout

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.room.Database
import com.amitshekhar.DebugDB
import com.example.layout.SongActivity
import com.example.layout.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var gson: Gson = Gson()
    private var songId: Int = 0
    private var nowPos: Int = 0
    private var mediaPlayer: MediaPlayer? = null

    private var songs = ArrayList<Song>()
    lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        inputDummyAlbums()
        inputDummySongs()
        setListener()

        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt", 0)
        editor.commit()


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
            val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putInt("songId", songId)
            editor.apply()
            val intent = Intent(this, SongActivity::class.java)
            val title = binding.mainCurrentTitleTv.text
            val artist = binding.mainCurrentArtistTv.text
            //title = song.title
            //artist = song.artist
            //playTime = song.playTime
            //isPlaying = song.isPlaying
            intent.putExtra("title", title)
            intent.putExtra("artist", artist)
//            intent.putExtra("music", song.music)
//            intent.putExtra("currentTime", song.second)
            startActivity(intent)
        }

    }

    private fun setInit() {
        if (songs.isEmpty()) {
            var DB = SongDatabase.getInstance(this)!!
            songs.addAll(DB.SongDao().getSongs())
        }

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        songId = spf.getInt("songId", 0)

        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                nowPos = i
                return
            }
        }
        nowPos = 1
    }

    private fun setminiplayer() {
        val music = resources.getIdentifier(songs[nowPos].music, "raw", this.packageName)
        binding.mainCurrentTitleTv.text = songs[nowPos].title
        binding.mainCurrentArtistTv.text = songs[nowPos].singer
        binding.mainCurrentPlaySb.progress = songs[nowPos].second*1000/songs[nowPos].playTime
        mediaPlayer = MediaPlayer.create(this, music)

        setStat()
    }

    private fun setStat() {
        if (songs[nowPos].isPlaying) {
            binding.mainCurrentPlayIv.setImageResource(R.drawable.btn_miniplay_mvplay)
            songs[nowPos].isPlaying = false
            mediaPlayer?.pause()
        }
        else {
            binding.mainCurrentPlayIv.setImageResource(R.drawable.btn_miniplay_mvpause)
            songs[nowPos].isPlaying = true
            mediaPlayer?.start()
        }


    }
    override fun onStart() {
        super.onStart()

        setInit()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val jsonSong = sharedPreferences.getString("song", null)
//        songId = sharedPreferences.getInt("songId", 0)
        DebugDB.getAddressLog()
//        val songDB = SongDatabase.getInstance(this)!!
//        song = if (songId == 0) {
//            songDB.SongDao().getSong(1)
//        }else {
//            songDB.SongDao().getSong(songId)
//        }
//        song = if (jsonSong == null) {
//            Song("first", "아이유", 215, false, "", 1)
//        }
//        else {
//            gson.fromJson(jsonSong, Song::class.java)
//        }
        startTimer()
        setminiplayer()

    }

    override fun onPause() {
        super.onPause()

        timer.interrupt() // 스레드를 해제함
        mediaPlayer?.release() // 미디어플레이어가 가지고 있던 리소스를 해방
        mediaPlayer = null // 미디어플레이어 해제

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)
        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)
        editor.apply()

    }

    private fun setListener() {
        binding.mainCurrentGobackIv.setOnClickListener {
            setId(-1)
        }

        binding.mainCurrentGoforeIv.setOnClickListener {
            setId(1)
        }

        binding.mainCurrentPlayIv.setOnClickListener {
            setStat()
        }
    }

    private fun setId(offset: Int) {

        if (nowPos + offset < 0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + offset >= songs.size){
            Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += offset
        timer.interrupt()
        startTimer()
        mediaPlayer?.release()
        mediaPlayer = null

        songs[nowPos].isPlaying = false
        setminiplayer()
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.AlbumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.AlbumDao().insert(
            Albums (
                1, "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
                    )
        )

        songDB.AlbumDao().insert(
            Albums(
                2,
                "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )

        songDB.AlbumDao().insert(
            Albums(
                3,
                "iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)", R.drawable.img_album_exp2
            )
        )

        songDB.AlbumDao().insert(
            Albums(
                4,
                "MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )

        songDB.AlbumDao().insert(
            Albums(
                5,
                "GREAT!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp2
            )
        )

    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.SongDao().getSongs()

        if (songs.isNotEmpty()) return

//        if (song.title == ""){}
//        else {
//            songDB.SongDao().insert(song)
//        }


        songDB.SongDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_night",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Butter (Hotter Remix)",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Butter (Sweeter Remix)",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_night",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Next Level (IMLAY Remix)",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_night",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "소우주 (Mikrokosmos)",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_night",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "Make It Right",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_night",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.SongDao().insert(
            Song(
                "궁금해",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_night",
                R.drawable.img_album_exp2,
                false
            )
        )

        val _songs = songDB.SongDao().getSongs()
        Log.d("DB_data", _songs.toString())

    }

    inner class Timer(private val playTime: Int = 0, var isPlaying: Boolean = false) : Thread() {
        private var second: Long = 0

        @SuppressLint("SetTextI18n")
        override fun run() {
            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(1000)
                        second++

                        runOnUiThread {
                            binding.mainCurrentPlaySb.progress =
                                (second * 1000 / playTime).toInt()
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SONG", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }
}