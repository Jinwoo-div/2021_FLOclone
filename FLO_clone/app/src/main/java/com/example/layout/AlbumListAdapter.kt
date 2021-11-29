package com.example.layout

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.layout.databinding.ItemRecylcerAlbumListBinding

class AlbumListAdapter(private val dataSet: ArrayList<List<String>>) :
    RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemRecylcerAlbumListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])


    }

    class ViewHolder(private val binding: ItemRecylcerAlbumListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:List<String>) {
            binding.albumListItemArtistTv.text = data[1]
            binding.albumListItemTitleTv.text = data[0]
            binding.albumListItemNumTv.text = data[2]
            
            itemView.setOnClickListener {
                Toast.makeText(binding.root.context, binding.albumListItemTitleTv.text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}