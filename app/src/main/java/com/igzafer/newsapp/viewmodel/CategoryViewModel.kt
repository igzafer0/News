package com.igzafer.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.igzafer.newsapp.model.NewsModel
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
import com.igzafer.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CategoryViewModel (app: Application,
                         val newsRepository: NewsRepository,

) : AndroidViewModel(app) {

    val allNewsByCategory: MutableLiveData<Resource<NewsModel>> = MutableLiveData()
    var allNewsByCategoryResponse: NewsModel? = null

    fun getNews(q:String) = viewModelScope.launch {
        getNewsByCategory(q)
    }
    private fun processAllNewsByCategory(response: Response<NewsModel>): Resource<NewsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (allNewsByCategoryResponse == null) {
                    allNewsByCategoryResponse = resultResponse
                } else {
                    val oldArticles = allNewsByCategoryResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(allNewsByCategoryResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private suspend fun getNewsByCategory(q: String) {
        allNewsByCategory.postValue(Resource.Loading())
        try {
            val response = newsRepository.getEverything(q)
            allNewsByCategory.postValue(processAllNewsByCategory(response))
        } catch (t: Throwable) {
            Log.d("winter", t.message.toString())
            when (t) {
                is IOException -> allNewsByCategory.postValue(Resource.Error("Ağ Hatası"))
                else -> allNewsByCategory.postValue(Resource.Error("Bilinmeyen Hata"))
            }
        }
    }

}