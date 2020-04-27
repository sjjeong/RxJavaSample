package com.dino.rxsample.week5.behaviorsubject

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dino.rxsample.R
import com.dino.rxsample.week5.behaviorsubject.bus.RxBus
import com.dino.rxsample.week5.behaviorsubject.event.SampleRxBusEvent
import kotlinx.android.synthetic.main.activity_sender.*

class SenderActivity : AppCompatActivity(R.layout.activity_sender) {

    fun send(view: View) {
        val text = et_input.text.toString()
        RxBus.onNext(SampleRxBusEvent(text))
    }

}