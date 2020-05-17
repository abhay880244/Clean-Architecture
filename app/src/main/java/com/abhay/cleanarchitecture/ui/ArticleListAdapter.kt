package com.abhay.cleanarchitecture.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhay.cleanarchitecture.R
import com.abhay.cleanarchitecture.databinding.ItemArticleBinding
import com.abhay.cleanarchitecture.models.Articles

class ArticleListAdapter: RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    private lateinit var articlesList:List<Articles>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemArticleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articlesList[position])
    }

    override fun getItemCount(): Int {
        return if(::articlesList.isInitialized) articlesList.size else 0
    }

    fun updateArticlesList(articlesList:List<Articles>){
        this.articlesList = articlesList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemArticleBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = ArticleViewModel()
        fun bind(articles: Articles){
            viewModel.bind(articles)
            binding.viewModel = viewModel
        }
    }
}