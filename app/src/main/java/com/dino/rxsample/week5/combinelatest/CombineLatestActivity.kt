package com.dino.rxsample.week5.combinelatest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.dino.rxsample.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_combine_latest.*

class CombineLatestActivity : AppCompatActivity(R.layout.activity_combine_latest) {

    private val nameBehaviorSubject = BehaviorSubject.createDefault("")
    private val ageBehaviorSubject = BehaviorSubject.createDefault("")
    private val addressBehaviorSubject = BehaviorSubject.createDefault("")

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et_name.doOnTextChanged { text, _, _, _ ->
            nameBehaviorSubject.onNext(text.toString())
        }
        et_age.doOnTextChanged { text, _, _, _ ->
            ageBehaviorSubject.onNext(text.toString())
        }
        et_address.doOnTextChanged { text, _, _, _ ->
            addressBehaviorSubject.onNext(text.toString())
        }

        listOf(
            nameBehaviorSubject,
            ageBehaviorSubject,
            addressBehaviorSubject
        ).combineLatest {
            it.fold(true, { t1, t2 -> t1 && t2.isNotEmpty() })
        }.subscribe {
            btn_send.isEnabled = it
        }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}