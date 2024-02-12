package com.example.movieapp.domain.repository

import com.example.movieapp.core.datasource.RemoteDataSource
import com.example.movieapp.di.IoDispatcher
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor(private val remoteDataSource: RemoteDataSource, @IoDispatcher private val dispatcherIO: CoroutineDispatcher): RemoteRepository {

    //don't hardcode dispatecher.io in flow. better put it as constructor in repo then inject it to flow

    override fun getNewMovie(page: Int): Flow<RemoteState<List<ResultsItem?>>> =
        flow {
            when (val result = remoteDataSource.getUpdatedMovie(page)) {
                is RemoteState.Success -> {
                    emit(RemoteState.Success(result.data))
                }

                is RemoteState.Error -> {
                    emit(RemoteState.Error(result.error))
                }
            }
        }.flowOn(dispatcherIO)


    override fun getDiscoverRatingMovie(page: Int): Flow<RemoteState<List<ResultsItem?>>> =
        flow {
            when (val result = remoteDataSource.getUpdatedMovie(page)) {
                is RemoteState.Success -> {
                    val filterMovie = result.data
                        .filter { it?.voteAverage!! > 7.0 }
                        .sortedByDescending { it?.voteAverage }
                    emit(RemoteState.Success(filterMovie))
                }

                is RemoteState.Error -> {
                    emit(RemoteState.Error(result.error))
                }
            }
        }.flowOn(dispatcherIO)

    override fun getMovieByGenre(page: Int, genre: Int): Flow<RemoteState<List<ResultsItem?>>> =
        flow {
            when (val result = remoteDataSource.getMovieByGenre(page, genre)) {
                is RemoteState.Success -> {
                    val filterMovie = result.data
                        .filter { it?.voteAverage!! > 7.0 }
                        .sortedByDescending { it?.voteAverage }
                    emit(RemoteState.Success(filterMovie))
                }

                is RemoteState.Error -> {
                    emit(RemoteState.Error(result.error))
                }
            }
        }.flowOn(dispatcherIO)

}