package com.igzafer.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
@Suppress("UNCHECKED_CAST")
open class CategoryBaseViewModel(
    val app: Application,
    val newsRepository: NewsRepository,
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(app, newsRepository) as T
    }

}