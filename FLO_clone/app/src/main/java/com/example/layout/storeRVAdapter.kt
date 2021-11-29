package com.example.layout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.layout.databinding.ItemRecyclerStoreListBinding
import com.example.layout.databinding.ItemStoreBinding

class storeRVAdapter(): RecyclerView.Adapter<storeRVAdapter.viewHolder>() {

    private val songList = ArrayList<Song>()

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var itemClickListener: MyItemClickListener

    fun setItemClickListener(itemClickListener: MyItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): storeRVAdapter.viewHolder {
        val binding: ItemStoreBinding = ItemStoreBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: storeRVAdapter.viewHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(songs: ArrayList<Song>) {
        this.songList.clear()
        this.songList.addAll(songs)

        notifyDataSetChanged()
    }

    fun showAl():ArrayList<Song> {
        return songList
    }

    fun removeAlbum(position: Int) {
        songList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class viewHolder(val binding: ItemStoreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemStoreAlbumIv.setImageResource(song.coverImg!!)
            binding.itemStoreAlbumArtistTv.text = song.singer
            binding.itemStoreAlbumTitleTv.text = song.title

        }

    }

}