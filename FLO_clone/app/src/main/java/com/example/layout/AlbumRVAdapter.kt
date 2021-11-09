package com.example.layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.layout.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.viewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.viewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return viewHolder(binding);
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.viewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{
            myItemClickListener.onItemClick(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size

    inner class viewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album:Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumArtistTv.text = album.singer
            binding.itemAlbumIv.setImageResource(album.coverImg!!)
        }

    }
}