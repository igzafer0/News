package com.igzafer.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
@Suppress("UNCHECKED_CAST")
open class NewsBaseViewModel(
    val app: Application,
    val newsRepository: NewsRepository,
    val categoryRepository: CategoryRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return NewsViewModel(app, newsRepository,categoryRepository) as T
    }


}