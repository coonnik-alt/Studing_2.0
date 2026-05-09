package com.example.practwork15

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.constraintlayout.motion.widget.KeyPosition
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practwork15.databinding.FragmentSecondBinding
import com.example.practwork15.databinding.ItemBinding

class Adapter(
    private var items: List<Item>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position : Int) {
        val item = items[position]
        holder.binding.photoDate.text = item.date
        Glide.with(holder.itemView.context)
            .load(item.uri)
            .centerCrop()
            .into(holder.binding.Image)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

}

class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)