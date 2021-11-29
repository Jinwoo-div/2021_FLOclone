package com.example.layout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.layout.databinding.FragmentHomeBinding
import com.google.gson.Gson


class home : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var albumDatas = ArrayList<Album>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val db = SongDatabase.getInstance(context as MainActivity)!!
        var albumdatas = db.AlbumDao().getAlbums()
        for (i in 0..albumdatas.size -1) {
            albumDatas.add(Album(albumdatas[i].title, albumdatas[i].singer, albumdatas[i].coverImg))
        }
//        albumDatas.apply {
//            add(Album("title", "bts", R.drawable.img_album_exp))
//            add(Album("title1", "bts1", R.drawable.img_album_exp2))
//            add(Album("title2", "bts2", R.drawable.img_album_exp))
//            add(Album("title3", "bts3", R.drawable.img_album_exp2))
//            add(Album("title4", "bts4", R.drawable.img_album_exp))
//            add(Album("title5", "bts5", R.drawable.img_album_exp2))
//        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }
        })
        binding.homeTodaymusicRv.adapter = albumRVAdapter

        binding.homeTodaymusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



        // Inflate the layout for this fragment
        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_fl, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("title", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.homeTodaymusicNode1Iv.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("title", binding.homeTodaymusicNode1TitleTv.text.toString())
//
//            val tran = (activity as MainActivity).supportFragmentManager.beginTransaction()
//            tran.replace(R.id.main_frame_fl, AlbumFragment())
//
//            tran.commit()
//        }
        val pagerAdapter = HomeAd1Adapter(requireActivity())
        pagerAdapter.addFragment(HomeAd1Fragment(R.drawable.img_home_viewpager_exp), 0)
        pagerAdapter.addFragment(HomeAd1Fragment(R.drawable.img_home_viewpager_exp2), 1)
        pagerAdapter.addFragment(HomeAd1Fragment(R.drawable.img_home_viewpager_exp), 2)
        pagerAdapter.addFragment(HomeAd1Fragment(R.drawable.img_home_viewpager_exp2), 3)

        binding.homeAd1Vp.adapter = pagerAdapter
    }
}