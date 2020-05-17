package com.abhay.cleanarchitecture.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhay.cleanarchitecture.R
import com.abhay.cleanarchitecture.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var errorSnackbar: Snackbar? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this,ViewModelFactory(this)).get(NewsListViewModel::class.java)
        binding.viewModel = viewModel


        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError() else hideError()
        })
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun showError() {
        errorSnackbar = Snackbar.make(binding.root, "An error occured while retrieving news", Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }
}
