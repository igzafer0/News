package com.igzafer.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.igzafer.newsapp.R
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
import com.igzafer.newsapp.viewmodel.CategoryBaseViewModel
import com.igzafer.newsapp.viewmodel.CategoryViewModel
import com.igzafer.newsapp.viewmodel.NewsBaseViewModel
import com.igzafer.newsapp.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel
    lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository()
        val categoryRepository = CategoryRepository()
        val newsViewModelProviderFactory = NewsBaseViewModel(application, newsRepository,categoryRepository)
        val categoryViewModelProviderFactory = CategoryBaseViewModel(application, newsRepository)
        newsViewModel = ViewModelProvider(this, newsViewModelProviderFactory).get(NewsViewModel::class.java)
        categoryViewModel = ViewModelProvider(this, categoryViewModelProviderFactory).get(CategoryViewModel::class.java)

    }



}