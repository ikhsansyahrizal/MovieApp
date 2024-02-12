package com.example.movieapp.core.datasource

import com.example.movieapp.api.RemoteApi
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(private val remoteApi: RemoteApi) : RemoteDataSource {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun getEncryptedKey(): String

    override suspend fun getUpdatedMovie(page: Int): RemoteState<List<ResultsItem?>> {
        return try {

            val result = remoteApi.getNewestMovie(
                accept = "application/json",
                authorization = getEncryptedKey(),
                include_adult = false,
                include_video = false,
                language = "en-US",
                page = page,
                sort_by = "popularity.desc"
            )

            when {
                result.isSuccessful -> {
                    val responseData = result.body()?.results
                    if (responseData != null) {
                        RemoteState.Success(responseData)
                    } else {
                        RemoteState.Error("Body is null")
                    }
                }

                else -> {
                    RemoteState.Error("failed fetch data")
                }
            }
        } catch (e: HttpException) {
            RemoteState.Error(e.localizedMessage)
        } catch (e: IOException) {
            RemoteState.Error("Can't reach server")
        } catch (e: Exception) {
            RemoteState.Error(e.localizedMessage)
        }
    }

    override suspend fun getMovieByGenre(page: Int, genre: Int): RemoteState<List<ResultsItem?>> {
        return try {

            val result = remoteApi.getMovieByGenre(
                accept = "application/json",
                authorization = getEncryptedKey(),
                include_adult = false,
                include_video = false,
                language = "en-US",
                page = page,
                sort_by = "popularity.desc",
                with_genres = genre
            )

            when {
                result.isSuccessful -> {
                    val responseData = result.body()?.results
                    if (responseData != null) {
                        RemoteState.Success(responseData)
                    } else {
                        RemoteState.Error("Body is null")
                    }
                }

                else -> {
                    RemoteState.Error("failed fetch data")
                }
            }
        } catch (e: HttpException) {
            RemoteState.Error(e.localizedMessage)
        } catch (e: IOException) {
            RemoteState.Error("Can't reach server")
        } catch (e: Exception) {
            RemoteState.Error(e.localizedMessage)
        }
    }
}

