package com.dino.rxsample.week6.rxmvvm.data

import io.reactivex.Single

interface RegisterRepository {

    fun register(email: String, password: String): Single<Boolean>

}