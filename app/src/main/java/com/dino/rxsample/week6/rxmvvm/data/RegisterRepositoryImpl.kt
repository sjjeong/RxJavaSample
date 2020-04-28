package com.dino.rxsample.week6.rxmvvm.data

import io.reactivex.Single
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class RegisterRepositoryImpl : RegisterRepository {

    override fun register(email: String, password: String): Single<Boolean> =
        Single.create<Boolean> {
            it.onSuccess(Random.nextBoolean())
        }.delay(2000L , TimeUnit.MILLISECONDS)


}