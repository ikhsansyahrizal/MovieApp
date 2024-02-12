package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.docotel.ikhsansyahrizal.doconewss.helper.DIFFUTIL_MOVIEITEM_CALLBACK
import com.example.movieapp.databinding.ListItemOneViewBinding
import com.example.movieapp.model.ResultsItem

class OneMovieAdapter(): RecyclerView.Adapter<OneMovieAdapter.OneMovieViewHolder>() {


    private val movies: MutableList<ResultsItem?> = mutableListOf()

    inner class OneMovieViewHolder(private val binding: ListItemOneViewBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(data: ResultsItem?, position: Int) {

            binding.tvRate.text = data?.voteAverage.toString()
            binding.tvOverview.text = data?.overview
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/${data?.posterPath.toString()}")
                .into(binding.imgPoster)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemOneViewBinding.inflate(inflater, parent, false)
        return OneMovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: OneMovieViewHolder, position: Int) {
        val currentItem = movies[position]
        holder.bind(currentItem, position)
    }

    fun setList(list: List<ResultsItem?>) {
        val startPosition = movies.size
        movies.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
     }

}