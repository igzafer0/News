package com.igzafer.newsapp.repo

import com.igzafer.newsapp.model.CategoryModel
import com.igzafer.newsapp.service.RetrofitInstance

class NewsRepository {
    suspend fun getNews(countryCode: String) =
        RetrofitInstance.api.getBreakingNews(countryCode)

    suspend fun getEverything() =
        RetrofitInstance.api.getEverything()

}