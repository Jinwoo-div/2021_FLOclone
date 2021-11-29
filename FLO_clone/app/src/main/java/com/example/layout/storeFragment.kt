package com.example.layout

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.example.layout.databinding.FragmentAlbumBinding
import com.example.layout.databinding.FragmentStoreBinding
import com.example.layout.databinding.ItemRecyclerStoreListBinding
import com.google.android.material.tabs.TabLayoutMediator


class store : Fragment() {
    private lateinit var binding: FragmentStoreBinding

    private lateinit var songDB: SongDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreBinding.inflate(inflater, container, false)

        initListener()
        val vpAdapter = StoreVPAdapter(requireActivity())
        vpAdapter.addFragment(RecyclerStoreListItem(0), "song")
        vpAdapter.addFragment(RecyclerStoreListItem(0), "temp")
        vpAdapter.addFragment(RecyclerStoreListItem(1), "album")

        binding.storeListVp.adapter = vpAdapter

        TabLayoutMediator(binding.storeTabTl, binding.storeListVp) {tab, position->
            when (position) {
                0 -> {
                    tab.text = "저장한 곡"
                }

                1-> {
                    tab.text = "음악파일"
               }

                2-> {
                    tab.text = "저장앨범"
                }
            }
        }.attach()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val spf =  activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val id = spf?.getInt("jwt", 0)
        val db = SongDatabase.getInstance(context as MainActivity)
        val user :User? = db?.UserDao()?.getUser(id)
        if (id != 0) {
            binding.storeLoginTv.text = "로그아웃"
            binding.storeUserNameTv.text = user?.name
        }
        else {
        }
    }

    fun initListener() {
        binding.storeLoginTv.setOnClickListener() {
            if (binding.storeLoginTv.text == "로그인") {
                val intent = Intent(context as MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
                val editor = spf?.edit()
                editor?.remove("jwt")
                editor?.apply()
                binding.storeLoginTv.text = "로그인"
                binding.storeUserNameTv.text = ""
            }
        }
    }

}
