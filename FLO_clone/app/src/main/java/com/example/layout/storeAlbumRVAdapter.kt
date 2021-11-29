package com.example.layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.layout.databinding.ItemRecyclerStoreAlbumListBinding

class storeAlbumRVAdapter(var list: ArrayList<Album>): RecyclerView.Adapter<storeAlbumRVAdapter.viewHolder>() {
    private var AlbumList = list

    override fun onCreateViewHolder(
        viewgroup: ViewGroup,
        viewType: Int
    ): storeAlbumRVAdapter.viewHolder {
        val binding : ItemRecyclerStoreAlbumListBinding = ItemRecyclerStoreAlbumListBinding.inflate(
            LayoutInflater.from(viewgroup.context), viewgroup, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(AlbumList[position])
    }

    override fun getItemCount(): Int {
        return AlbumList.size
    }

    fun addData(data: ArrayList<Albums>) {
        AlbumList.clear()
        for (i in 0..data.size-1) {
            AlbumList.add(Album(data[i].title, data[i].singer, data[i].coverImg))
        }
    }

    inner class viewHolder(val binding: ItemRecyclerStoreAlbumListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.storeAlbumListTitleTv.text = album.title
            binding.storeAlbumListArtistTv.text = album.singer
            binding.storeAlbumListImgIv.setImageResource(album.coverImg!!)
        }
    }
}