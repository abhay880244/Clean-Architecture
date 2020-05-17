package com.abhay.cleanarchitecture.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhay.cleanarchitecture.R
import com.abhay.cleanarchitecture.databinding.ActivityMainBinding
import com.abhay.cleanarchitecture.models.NewsResponse
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var errorSnackbar: Snackbar? = null

    var disposable  = CompositeDisposable()

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.postList.addItemDecoration(DividerItemDecoration(this,LinearLayout.VERTICAL))

        viewModel = ViewModelProviders.of(this,ViewModelFactory(this)).get(NewsListViewModel::class.java)
        binding.viewModel = viewModel


        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError() else hideError()
        })

        var observableQueryText = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (!emitter.isDisposed) {
                            newText?.let { emitter.onNext(it) } // Pass the query to the emitter
                        }
                        return false
                    }

                })
            }

        })

        observableQueryText
            .doOnSubscribe { disposable.add(it) }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .switchMap<NewsResponse> { query -> viewModel.newsApiInterface.getNews(query) }
            .observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .subscribe(
                {
                    if(it.articles!=null && it.articles.isNotEmpty()){
                        viewModel.articleListAdapter.updateArticlesList(it.articles)
                        hideError()
                    }
                    else{
                        viewModel.articleListAdapter.updateArticlesList(it.articles)
                        errorSnackbar = Snackbar.make(binding.root, "No News Found", Snackbar.LENGTH_INDEFINITE)
                        errorSnackbar?.show()
                    }
                },
                {showError()}
        )


    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun showError() {
        errorSnackbar = Snackbar.make(binding.root, "An error occured while retrieving news", Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
