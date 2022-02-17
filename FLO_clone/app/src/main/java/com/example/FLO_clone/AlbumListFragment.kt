package com.example.FLO_clone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.FLO_clone.databinding.FragmentAlbumListBinding
import java.util.ArrayList

class AlbumListFragment: Fragment() {
    lateinit var binding: FragmentAlbumListBinding
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        addData()
        binding.albumListRv.adapter = AlbumListAdapter(dataSet)

        return binding.root
    }


    private fun addData() {
        var db = SongDatabase.getInstance(context as MainActivity)!!
        var songs = db.SongDao().getSongs()
        for (i in 0..songs.size - 1) {
            dataSet.add(listOf(songs[i].title, songs[i].singer, i.toString()))
        }
    }

}