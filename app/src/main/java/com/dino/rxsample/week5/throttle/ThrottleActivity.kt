package com.dino.rxsample.week5.throttle

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dino.rxsample.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_debounce.*
import java.util.concurrent.TimeUnit

class ThrottleActivity : AppCompatActivity(R.layout.activity_throttle) {

    private var num: Int = 0

    private val behaviorSubject = BehaviorSubject.createDefault(Unit)

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        behaviorSubject.throttleFirst(2000L, TimeUnit.MILLISECONDS)
            .subscribe {
                num++
                tv_output.text = num.toString()
            }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun increase(v: View) {
        behaviorSubject.onNext(Unit)
    }


}