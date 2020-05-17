package com.abhay.cleanarchitecture.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.abhay.cleanarchitecture.db.ArticleDAO
import com.abhay.cleanarchitecture.models.Articles
import com.abhay.cleanarchitecture.network.NewsApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel(private val articleDao: ArticleDAO) : BaseViewModel() {

    val articleListAdapter = ArticleListAdapter()
    @Inject
    lateinit var newsApiInterface: NewsApiInterface

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadNews() }


    private lateinit var subscription: Disposable

    init {
        loadNews()
    }


    private fun loadNews() {
        subscription =
            Observable.fromCallable { articleDao.all }
                .concatMap { dbArticlesList ->
                    if (dbArticlesList.isEmpty()) {
                        newsApiInterface.getNews().concatMap { response ->
                            articleDao.insertAll(*response.articles.toTypedArray())
                            Observable.just(response.articles)
                        }
                    } else {
                        Observable.just(dbArticlesList)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { onRetrieveNewsListFinish() }
                .doOnSubscribe { onRetrieveNewsListStart() }
                .subscribe(
                    { onRetrieveNewsListSuccess(it) },
                    { onRetrieveNewsListError() }
                )
    }

    private fun onRetrieveNewsListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrieveNewsListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveNewsListSuccess(articles: List<Articles>) {
        articleListAdapter.updateArticlesList(articles)
    }

    private fun onRetrieveNewsListError() {
        errorMessage.value = 1
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}