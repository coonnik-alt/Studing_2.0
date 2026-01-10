package com.example.recycleview_retrofit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycleview_retrofit.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter <MovieViewHolder>() {

    val items = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<Movie>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): MovieViewHolder {

        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false)

        return MovieViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = items[position]

        // Заголовок: любой, который есть
        val title = movie.name ?: movie.enName ?: movie.alternativeName ?: ""
        holder.binding.titleText.text = title

        // Описание: полное или короткое
        val desc = movie.description ?: movie.shortDescription ?: ""
        holder.binding.text.text = desc

        // Постер: в твоём варианте 1 он должен быть всегда
        val posterUrl = movie.poster?.url

        Glide.with(holder.itemView)
            .load(posterUrl)
            .into(holder.binding.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class MovieViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder (binding.root){

}