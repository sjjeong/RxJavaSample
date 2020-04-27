package com.dino.rxsample.week6.retrofit

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dino.rxsample.R
import com.dino.rxsample.week6.retrofit.network.NetworkManager
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit.*

class RetrofitActivity : AppCompatActivity(R.layout.activity_retrofit) {

    private val compositeDisposable = CompositeDisposable()

    fun send(view: View) {
        val query = et_input.text.toString()
        NetworkManager.naverApi
            .getMovies(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tv_output.text = Gson().toJson(it)
            }, {
                tv_output.text = it.message ?: ""
            }).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}