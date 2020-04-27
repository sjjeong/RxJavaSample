package com.dino.rxsample.week5.behaviorsubject.bus

import io.reactivex.rxkotlin.ofType
import io.reactivex.subjects.BehaviorSubject

object RxBus {

    val behaviorSubject: BehaviorSubject<RxBusEvent> = BehaviorSubject.create()

    fun onNext(event: RxBusEvent) {
        behaviorSubject.onNext(event)
    }

    inline fun <reified T : RxBusEvent> subscribe(crossinline onNext: (T) -> Unit) =
        behaviorSubject
            .ofType<T>()
            .subscribe { onNext(it) }

}