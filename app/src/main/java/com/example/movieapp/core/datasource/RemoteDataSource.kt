package com.example.movieapp.core.datasource

import com.example.movieapp.model.Response
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState

interface RemoteDataSource {

    suspend fun getUpdatedMovie(page: Int): RemoteState<List<ResultsItem?>>

    suspend fun getMovieByGenre(page: Int, genre: Int): RemoteState<List<ResultsItem?>>

}