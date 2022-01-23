package com.igzafer.newsapp.service

import com.igzafer.newsapp.model.NewsModel
import com.igzafer.newsapp.util.Constants.Companion.API_KEY
import com.igzafer.newsapp.util.Constants.Companion.PAGE_SIZE
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface IEndPoints {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "tr",
        @Query("pageSize")
        pageNumber:Int = PAGE_SIZE,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsModel>
    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q")
        q: String = "turkey",
        @Query("pageSize")
        pageSize: Int = PAGE_SIZE,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsModel>
}