package com.example.movieapp.domain.repository

import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun getNewMovie(page: Int): Flow<RemoteState<List<ResultsItem?>>>
    fun getDiscoverRatingMovie(page: Int): Flow<RemoteState<List<ResultsItem?>>>
    fun getMovieByGenre(page: Int, genre: Int): Flow<RemoteState<List<ResultsItem?>>>

}