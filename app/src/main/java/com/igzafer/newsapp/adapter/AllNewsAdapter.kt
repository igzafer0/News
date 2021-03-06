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
import com.igzafer.newsapp.R
import com.igzafer.newsapp.databinding.EverynewsRowBinding
import com.igzafer.newsapp.databinding.NewsRowBinding
import com.igzafer.newsapp.model.Article
import com.igzafer.newsapp.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.news_row.view.*

class AllNewsAdapter: RecyclerView.Adapter<AllNewsAdapter.NewsViewHolder>(),IRowClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            if(oldItem.content==newItem.content){
                Log.d("winter","same")
            }else{
                Log.d("winter","not same")

            }
            return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            if(oldItem.content==newItem.content){
                Log.d("winter","same")
            }else{
                Log.d("winter","not same")

            }
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<EverynewsRowBinding>(inflater, R.layout.everynews_row, parent, false)
        return NewsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class NewsViewHolder(var view: EverynewsRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.view.news = article
        holder.view.newsPhoto.clipToOutline=true
        holder.view.listener=this
    }

    override fun rowTiklandi(view: View) {
        Log.d("winter",view.newsUrl.text.toString())
        val action = HomeFragmentDirections.actionHomeFragmentToReadFragment(view.newsUrl.text.toString())
        Navigation.findNavController(view).navigate(action)
    }
}