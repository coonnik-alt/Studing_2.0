package com.example.recycleview_retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class Retrofit(){

    @GET("v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int = 2,
        @Query("limit") limit: Int = 1
    ): MoviesResponse {
    }


}
