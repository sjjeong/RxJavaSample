package com.dino.rxsample.week5.debounce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.dino.rxsample.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_debounce.*
import java.util.concurrent.TimeUnit

class DebounceActivity : AppCompatActivity(R.layout.activity_debounce) {

    private val publishSubject = PublishSubject.create<String>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et_input.doOnTextChanged { text, _, _, _ ->
            publishSubject.onNext(text.toString())
        }
        publishSubject.debounce(1000, TimeUnit.MILLISECONDS, Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                tv_output.text = it
            }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}