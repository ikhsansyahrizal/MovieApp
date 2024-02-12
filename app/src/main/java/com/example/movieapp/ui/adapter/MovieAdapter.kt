package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.docotel.ikhsansyahrizal.doconewss.helper.DIFFUTIL_MOVIEITEM_CALLBACK
import com.example.movieapp.databinding.ListItemBinding
import com.example.movieapp.model.ResultsItem

class MovieAdapter(): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    private val movies: MutableList<ResultsItem?> = mutableListOf()

    inner class MovieViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(data: ResultsItem?, position: Int) {

            binding.tvRate.text = data?.voteAverage.toString()
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/${data?.posterPath.toString()}")
                .into(binding.imgPoster)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movies[position]
        holder.bind(currentItem, position)
    }

    fun setList(list: List<ResultsItem?>) {
        val startPosition = movies.size
        movies.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
    }
}