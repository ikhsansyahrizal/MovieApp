package com.example.movieapp.api

import com.example.movieapp.helper.Constants
import com.example.movieapp.model.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RemoteApi {

    @GET(Constants.NEW_MOVIE)
    suspend fun getNewestMovie(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("include_video") include_video: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String,

        ): retrofit2.Response<Response>


    @GET(Constants.NEW_MOVIE)
    suspend fun getMovieByGenre(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("include_video") include_video: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String,
        @Query("with_genres") with_genres: Int

        ): retrofit2.Response<Response>


}