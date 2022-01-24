package com.igzafer.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.igzafer.newsapp.R
import com.igzafer.newsapp.adapter.AllNewsAdapter
import com.igzafer.newsapp.adapter.CategoryAdapter
import com.igzafer.newsapp.adapter.NewsAdapter
import com.igzafer.newsapp.model.Article
import com.igzafer.newsapp.model.NewsModel
import com.igzafer.newsapp.repo.CategoryRepository
import com.igzafer.newsapp.repo.NewsRepository
import com.igzafer.newsapp.util.Resource
import com.igzafer.newsapp.viewmodel.CategoryBaseViewModel
import com.igzafer.newsapp.viewmodel.NewsBaseViewModel
import com.igzafer.newsapp.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recy
import kotlinx.android.synthetic.main.fragment_read.*
import kotlinx.android.synthetic.main.fragment_read.backButton
import kotlinx.coroutines.*


@DelicateCoroutinesApi
class CategoryFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d("winter", "onstart")

    }

    var id: String = ""
    var categoryTitle: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = (activity as MainActivity).categoryViewModel
        arguments?.let {
            id = CategoryFragmentArgs.fromBundle(it).categoryId
            categoryTitle = CategoryFragmentArgs.fromBundle(it).categoryName
        }

        kategoriIsmi.text=categoryTitle
        setupRecyclerView()
        observers()
        GlobalScope.launch {
            Log.d("winter", "calisti")
            viewModel.allNewsByCategoryResponse = null
            viewModel.getNews(id)
        }
        backButtons.setOnClickListener {
           val action= CategoryFragmentDirections.actionCategoryFragmentPop()
            Navigation.findNavController(view).navigate(action)
        }
    }


    private fun observers() {
        viewModel.allNewsByCategory.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->

                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.d("winter", message)
                    }
                }
                else -> {}
            }
        })

    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter("category")
        recy.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }

    }

}