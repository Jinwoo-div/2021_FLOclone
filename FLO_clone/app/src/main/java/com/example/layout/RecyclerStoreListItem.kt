package com.example.layout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.layout.databinding.ItemRecyclerStoreListBinding

class RecyclerStoreListItem(val set: Int): Fragment() {
    lateinit var binding: ItemRecyclerStoreListBinding
    private val albumList = ArrayList<Album>()

    private lateinit var songDB: SongDatabase

    lateinit var albumDB: SongDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        binding = ItemRecyclerStoreListBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        if (set == 0) {
            val storeRVAdapter = storeRVAdapter()

            storeRVAdapter.setItemClickListener(object : storeRVAdapter.MyItemClickListener {
                override fun onRemoveSong(songId: Int) {
                    albumDB.SongDao().updateIsLikeById(false, songId)
                }
            })

            binding.storeListRv.adapter = storeRVAdapter

            storeRVAdapter.addAlbums(songDB.SongDao().getLikedSongs(true) as ArrayList<Song>)
        }

        else {
            val db = SongDatabase.getInstance(context as MainActivity)!!
            val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
            val id = spf?.getInt("jwt", 0)
            if (id != 0) {
                val likeList: List<Like> = db.LikeDao().getLikedAlbum(id)
                var data = ArrayList<Album>()
                for (i in 0..likeList.size - 1) {
                    val albums = db.AlbumDao().getAlbumsById(likeList[i].albumId)
                    val albumData = Album(albums.title, albums.singer, albums.coverImg)
                    data.add(albumData)
                }
                val adapter = storeAlbumRVAdapter(data)

                binding.storeListRv.adapter = adapter
                var datas = ArrayList<Albums>()
                val al = db.LikeDao().getLikedAlbum(id)
                for (i in 0..al.size - 1) {
                    datas.add(db.AlbumDao().getAlbumsById(al[i].albumId))
                }
                adapter.addData(datas)
            }
    }



    return binding.root
    }

    private fun addData() {
        albumList.apply {
            add(Album("title", "bts", R.drawable.img_album_exp))
//            add(Album("butter", "bts", R.drawable.img_album_exp2))
//            add(Album("butter", "bts", R.drawable.img_album_exp2))
//            add(Album("butter", "bts", R.drawable.img_album_exp))
//            add(Album("butter", "bts", R.drawable.img_album_exp2))
//            add(Album("butter", "bts", R.drawable.img_album_exp))
        }
    }
}