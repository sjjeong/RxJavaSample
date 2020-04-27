package com.dino.rxsample.week5.behaviorsubject.event

import com.dino.rxsample.week5.behaviorsubject.bus.RxBusEvent

data class SampleRxBusEvent(val text: String) : RxBusEvent