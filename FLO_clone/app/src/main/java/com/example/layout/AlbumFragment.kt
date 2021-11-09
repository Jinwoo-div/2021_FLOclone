package com.example.layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.layout.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlin.math.log

class AlbumFragment: Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private lateinit var goback: OnBackPressedCallback
    private var gson: Gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val view = binding.albumInfoVp
        val albumData = arguments?.getString("title")
        val album = gson.fromJson(albumData, Album::class.java)
        setInit(album)

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumInfoThumbnailLeftV.setImageResource(album.coverImg!!)
        binding.albumInfoTitleTv.text = album.title.toString()
        binding.albumInfoArtistEtcTv.text = album.title.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = AlbumPageAdapter(requireActivity())

        pagerAdapter.addFragment(AlbumListFragment(), "a")
        pagerAdapter.addFragment(store(), "b")
        pagerAdapter.addFragment(search(), "c")

        binding.albumInfoVp.adapter = pagerAdapter
        binding.albumInfoVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "page ${position + 1}")
            }
        })

        TabLayoutMediator(binding.albumInfoTabTl, binding.albumInfoVp) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "수록곡"
                }
                1 -> {
                    tab.text = "상세정보"
                }
                2 -> {
                    tab.text = "영상"
                }
            }
        }.attach()

        binding.albumSetGobackBtn.setOnClickListener {

            val tran = (activity as MainActivity).supportFragmentManager.beginTransaction()
            tran.replace(R.id.main_frame_fl, home())

            tran.commit()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        goback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_fl, home())
                    .commitAllowingStateLoss()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, goback)
    }

}