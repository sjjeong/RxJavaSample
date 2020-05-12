package com.dino.rxsample.week6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dino.rxsample.R
import com.dino.rxsample.week6.network.NetworkManager
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.merge
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_naver_movie_search.*
import java.util.concurrent.TimeUnit

class NaverMovieSearchActivity : AppCompatActivity(R.layout.activity_naver_movie_search) {

    private val compositeDisposable = CompositeDisposable()

    private val vm by lazy {
        NaverMovieSearchViewModel(NetworkManager.naverApi)
    }

    private val naverMovieSearchAdapter = NaverMovieSearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        rv_content.adapter = naverMovieSearchAdapter
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    private fun bindViewModel() {
        val textChange = RxTextView.textChanges(et_input)
            .map { it.toString() }
            .debounce(1500L, TimeUnit.MILLISECONDS, Schedulers.computation())
        val searchClick = RxView.clicks(btn_search)
            .map { et_input.text.toString() }
        listOf(textChange, searchClick)
            .merge()
            .throttleFirst(1000L, TimeUnit.MILLISECONDS)
            .filter(String::isNotBlank)
            .subscribe(vm::searchMovie)
            .addTo(compositeDisposable)

        vm.errorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::showError)
            .addTo(compositeDisposable)
        vm.loadingSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pb_loading.isVisible = it }
            .addTo(compositeDisposable)
        vm.movieItemsSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(naverMovieSearchAdapter::resetAll)
            .addTo(compositeDisposable)
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        unbindViewModel()
        super.onPause()
    }

    private fun unbindViewModel() {
        compositeDisposable.clear()
        vm.unbindViewModel()
    }

}