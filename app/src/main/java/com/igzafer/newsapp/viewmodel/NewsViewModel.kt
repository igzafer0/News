package com.igzafer.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.igzafer.newsapp.model.CategoryModel
import com.igzafer.newsapp.model.NewsModel
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
import com.igzafer.newsapp.util.Constants
import com.igzafer.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository,
    val categoryRepository: CategoryRepository
) : AndroidViewModel(app) {
    //isteklerimizin yanıtlarını burada ele alacağız
    //burada live data mız olacak
    //bu yapılan isteklerle ilgili bir depişiklik olduğunda live data fragmentları bu değişiklikler hakkında bilgilendirecek

    //live data bizim fragment larımız için kullanılır. Böylece fragmentler gözlemci(observers) olarak bu live data ya abone(subscribe) olabilir


    val breakingNews: MutableLiveData<Resource<NewsModel>> = MutableLiveData()
    val allNews: MutableLiveData<Resource<NewsModel>> = MutableLiveData()
    val category: MutableLiveData<List<CategoryModel>> = MutableLiveData()

    private var breakingNewsResponse: NewsModel? = null
    private var allNewsResponse: NewsModel? = null

    init {
        getBreakingNews()
        getAllNews()
        prepareCategory()
    }


    fun getBreakingNews() = viewModelScope.launch {
        breakingNewsCall(Constants.COUNTRY_CODE)
    }

    fun getAllNews() = viewModelScope.launch {
        allNewsCall()
    }

    fun prepareCategory() = viewModelScope.launch {
        categoryCall()
    }

    private fun processBreakingNews(response: Response<NewsModel>): Resource<NewsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun processAllNewsResponse(response: Response<NewsModel>): Resource<NewsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (allNewsResponse == null) {
                    allNewsResponse = resultResponse
                } else {
                    val oldArticles = allNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(allNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun breakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {

            val response = newsRepository.getNews(countryCode)

            breakingNews.postValue(processBreakingNews(response))


        } catch (t: Throwable) {
            Log.d("winter", t.message.toString())
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Ağ Hatası"))
                else -> breakingNews.postValue(Resource.Error("Bilinmeyen Hata"))
            }
        }
    }

    private suspend fun allNewsCall() {

        try {
            val response = newsRepository.getEverything()
            Log.d("winter", response.code().toString())
            Log.d("winter", "calisti")

            allNews.postValue(processAllNewsResponse(response))


        } catch (t: Throwable) {
            Log.d("winter", t.message.toString())
            when (t) {
                is IOException -> allNews.postValue(Resource.Error("Ağ Hatası"))
                else -> allNews.postValue(Resource.Error("Bilinmeyen hata"))
            }
        }
    }

    private fun categoryCall() {

        try {

            val response = categoryRepository.prepareCategory()

            category.postValue(response)


        } catch (t: Throwable) {
            Log.d("winter", t.message.toString())

        }
    }
}

