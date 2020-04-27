package com.dino.rxsample.week5.behaviorsubject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dino.rxsample.R
import com.dino.rxsample.week5.behaviorsubject.bus.RxBus
import com.dino.rxsample.week5.behaviorsubject.event.SampleRxBusEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class BehaviorSubjectActivity : AppCompatActivity(R.layout.activity_behavior_subject) {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.subscribe<SampleRxBusEvent> {
            Log.e("log tag", it.text)
        }.addTo(compositeDisposable)
    }

    fun goToSenderActivity(view: View) {
        startActivity(Intent(this, SenderActivity::class.java))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}