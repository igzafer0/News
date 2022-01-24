package com.igzafer.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.igzafer.newsapp.R
import com.igzafer.newsapp.adapter.NewsAdapter
import com.igzafer.newsapp.util.Constants.Companion.DELAY
import com.igzafer.newsapp.util.Resource
import com.igzafer.newsapp.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
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
        newsAdapter = NewsAdapter("search")
        recySearch.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).categoryViewModel
        setupRecyclerView()
        observers()
        var job: Job? = null

        searchEt.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.allNewsByCategoryResponse = null
                        viewModel.getNews(editable.toString())
                    }
                }
            }
        }
    }
}