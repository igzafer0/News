package com.igzafer.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.igzafer.newsapp.R
import com.igzafer.newsapp.adapter.AllNewsAdapter
import com.igzafer.newsapp.adapter.CategoryAdapter
import com.igzafer.newsapp.adapter.NewsAdapter
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
import com.igzafer.newsapp.util.Resource
import com.igzafer.newsapp.viewmodel.BaseViewModel
import com.igzafer.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var allNewsAdapter: AllNewsAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository()
        val categoryRepository = CategoryRepository()
        val viewModelProviderFactory = BaseViewModel(application, newsRepository,categoryRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

    }



}