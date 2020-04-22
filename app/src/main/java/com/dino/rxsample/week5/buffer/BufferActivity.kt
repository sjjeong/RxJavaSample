package com.dino.rxsample.week5.buffer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dino.rxsample.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class BufferActivity : AppCompatActivity(R.layout.activity_buffer) {

    private val behaviorSubject = BehaviorSubject.createDefault(0L)

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        behaviorSubject.buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe {
                if (it.second - it.first < 2000L) {
                    super.onBackPressed()
                } else {
                    Toast.makeText(this, "앱을 종료 하려면 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                }
            }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onBackPressed() {
        behaviorSubject.onNext(System.currentTimeMillis())
    }

}