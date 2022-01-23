package com.igzafer.newsapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igzafer.newsapp.R
import com.igzafer.newsapp.databinding.NewsRowBinding
import com.igzafer.newsapp.model.Article
import com.igzafer.newsapp.model.NewsModel
import com.igzafer.newsapp.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.news_row.view.*

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(),IRowClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<NewsRowBinding>(inflater, R.layout.news_row, parent, false)
        return NewsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class NewsViewHolder(var view: NewsRowBinding) : RecyclerView.ViewHolder(view.root) {

    }
    override fun rowTiklandi(view: View) {
        Log.d("winter","row")
        val action = HomeFragmentDirections.actionHomeFragmentToReadFragment(view.newsUrl.text.toString())
        Navigation.findNavController(view).navigate(action)
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.view.news = article
        holder.view.newsPhoto.clipToOutline=true
        holder.view.listener=this


    }


}





