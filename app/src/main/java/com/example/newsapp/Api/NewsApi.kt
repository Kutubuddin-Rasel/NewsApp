package com.example.newsapp.Api

import com.example.newsapp.module.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHealines(
        @Query("country") country:String="us",
        @Query("category") category:String,
        @Query("page") pageNumber:Int,
        @Query("apiKey") apikey:String
    ):Response<News>

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") searchquery:String,
        @Query("sortBy") sortBy:String,
        @Query("pageSize") pageNumber:Int=10,
        @Query("apiKey") apikey:String
    ):Response<News>
}