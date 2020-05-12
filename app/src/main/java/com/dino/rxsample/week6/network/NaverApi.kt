package com.dino.rxsample.week6.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {

    @GET("v1/search/movie.json")
    fun getMovies(@Query("query") query: String): Single<NaverMovieResponse>

}