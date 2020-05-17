package com.abhay.cleanarchitecture.ui

import androidx.lifecycle.MutableLiveData
import com.abhay.cleanarchitecture.models.Articles

class ArticleViewModel : BaseViewModel() {
        private val title = MutableLiveData<String>()
        private val description = MutableLiveData<String>()

        fun bind(article: Articles){
            title.value = article.title
            description.value = article.description
        }

        fun getTitle():MutableLiveData<String>{
            return title
        }

        fun getDescription():MutableLiveData<String>{
            return description
        }

}