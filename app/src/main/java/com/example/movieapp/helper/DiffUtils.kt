package com.docotel.ikhsansyahrizal.doconewss.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.model.ResultsItem

val DIFFUTIL_MOVIEITEM_CALLBACK = object: DiffUtil.ItemCallback<ResultsItem>(){
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem == newItem
    }
}