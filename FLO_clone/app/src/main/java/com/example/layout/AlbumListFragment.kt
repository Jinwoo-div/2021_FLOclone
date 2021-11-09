package com.example.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.layout.databinding.FragmentAlbumListBinding
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
        for (i in 0..99) {
            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }

}