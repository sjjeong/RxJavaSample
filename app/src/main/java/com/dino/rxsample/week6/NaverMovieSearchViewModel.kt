package com.dino.rxsample.week6

import com.dino.rxsample.week6.network.NaverApi
import com.dino.rxsample.week6.network.NaverMovieResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class NaverMovieSearchViewModel(private val naverApi: NaverApi) {

    private val compositeDisposable = CompositeDisposable()

    val errorSubject = BehaviorSubject.create<Throwable>()

    val loadingSubject = BehaviorSubject.createDefault(false)

    val movieItemsSubject = BehaviorSubject.create<List<NaverMovieResponse.Item>>()

    fun searchMovie(query: String) {
        naverApi.getMovies(query)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { loadingSubject.onNext(true) }
            .doAfterTerminate { loadingSubject.onNext(false) }
            .map { it.items }
            .subscribe(movieItemsSubject::onNext, errorSubject::onNext)
            .addTo(compositeDisposable)
    }

    fun unbindViewModel() {
        compositeDisposable.clear()
    }
}