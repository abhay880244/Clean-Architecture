package com.abhay.cleanarchitecture.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.abhay.cleanarchitecture.models.Articles
import com.abhay.cleanarchitecture.network.NewsApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel : BaseViewModel() {

    val articleListAdapter = ArticleListAdapter()
    @Inject
    lateinit var newsApiInterface: NewsApiInterface

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadNews() }


    private lateinit var subscription: Disposable

    init {
        loadNews()
    }



    private fun loadNews() {
        subscription = newsApiInterface.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { onRetrieveNewsListFinish() }
            .doOnSubscribe { onRetrieveNewsListStart() }
            .subscribe(
                {onRetrieveNewsListSuccess(it.articles)},
                {onRetrieveNewsListError()}
            )
    }

    private fun onRetrieveNewsListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrieveNewsListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveNewsListSuccess(articles: List<Articles>) {
        articleListAdapter.updateArticlesList(articles)
    }

    private fun onRetrieveNewsListError(){
        errorMessage.value = 1
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}