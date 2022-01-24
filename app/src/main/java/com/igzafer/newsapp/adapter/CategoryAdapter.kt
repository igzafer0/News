package com.igzafer.newsapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igzafer.newsapp.R
import com.igzafer.newsapp.databinding.CategoryRowBinding
import com.igzafer.newsapp.databinding.EverynewsRowBinding
import com.igzafer.newsapp.model.Article
import com.igzafer.newsapp.model.CategoryModel
import com.igzafer.newsapp.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.category_row.view.*
import kotlinx.android.synthetic.main.news_row.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(),IRowClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<CategoryRowBinding>(inflater, R.layout.category_row, parent, false)
        return CategoryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class CategoryViewHolder(var view: CategoryRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.view.category=category
        holder.view.categoryPhoto.clipToOutline=true
        holder.view.listener=this

    }

    override fun rowTiklandi(view: View) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(view.idText.text.toString(),view.categoryTitle.text.toString())
        Navigation.findNavController(view).navigate(action)
    }

}