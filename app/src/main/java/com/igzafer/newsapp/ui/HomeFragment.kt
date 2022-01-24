package com.igzafer.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.igzafer.newsapp.R
import com.igzafer.newsapp.adapter.AllNewsAdapter
import com.igzafer.newsapp.adapter.CategoryAdapter
import com.igzafer.newsapp.adapter.NewsAdapter
import com.igzafer.newsapp.util.Resource
import com.igzafer.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


@DelicateCoroutinesApi
class HomeFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var allNewsAdapter: AllNewsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRl.setOnClickListener {
            try {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                Navigation.findNavController(view).navigate(action)
            }catch (e:Throwable){
               Log.d("winter",e.message.toString())
            }

        }

        viewModel = (activity as MainActivity).newsViewModel
        setupRecyclerView()
        observers()

    }

    private fun observers() {
        viewModel.breakingNews.observe(viewLifecycleOwner, { response ->
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
        viewModel.allNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                        allNewsAdapter.differ.submitList(newsResponse.articles.toList())
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
        viewModel.category.observe(viewLifecycleOwner, { response ->
            response?.let { categoryResponse ->
                categoryAdapter.differ.submitList(categoryResponse)
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter("home")
        allNewsAdapter = AllNewsAdapter()
        categoryAdapter = CategoryAdapter()
        recy.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
        recyEvery.apply {
            adapter = allNewsAdapter

            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


        }
        categoryRecy.apply {
            adapter = categoryAdapter

            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}