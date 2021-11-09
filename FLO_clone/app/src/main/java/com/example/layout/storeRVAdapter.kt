package com.example.layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.layout.databinding.ItemAlbumBinding
import com.example.layout.databinding.ItemRecyclerStoreBinding
import com.example.layout.databinding.ItemRecyclerStoreListBinding

class storeRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.viewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.viewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false) {

        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.viewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class viewHolder(val binding: ItemRecyclerStoreListBinding): RecyclerView.ViewHolder(binding.root) {


    }

}