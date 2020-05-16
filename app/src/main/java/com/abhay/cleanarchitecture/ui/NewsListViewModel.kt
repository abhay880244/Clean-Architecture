package com.abhay.cleanarchitecture.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.abhay.cleanarchitecture.network.NewsApiInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel : BaseViewModel() {

    @Inject
    lateinit var newsApiInterface: NewsApiInterface

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
        loadNews()
    }



    private fun loadNews() {
        subscription = newsApiInterface.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { onRetrieveNewsListError() }
            .doOnSubscribe { onRetrieveNewsListFinish() }
            .subscribe(
                {onRetrieveNewsListSuccess()},
                {onRetrieveNewsListError()}
            )
    }

    private fun onRetrieveNewsListStart(){
        loadingVisibility.value = View.VISIBLE

    }

    private fun onRetrieveNewsListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveNewsListSuccess(){

    }

    private fun onRetrieveNewsListError(){

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}