package com.dino.rxsample.week6.rxmvvm

import com.dino.rxsample.week6.rxmvvm.data.RegisterRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.rxkotlin.zip
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RxMvvmViewModel(private val registerRepository: RegisterRepository) {
    val emailSubject = BehaviorSubject.create<String>()

    val passwordSubject = BehaviorSubject.create<String>()

    val passwordCheckSubject = BehaviorSubject.create<String>()

    val loadingSubject = BehaviorSubject.createDefault(false)

    val registerEnableSubject =
        listOf(
            emailSubject.map { it.isNotBlank() },
            passwordSubject.map { it.isNotBlank() },
            passwordCheckSubject.map { it.isNotBlank() },
            loadingSubject.map { !it }
        )
            .combineLatest {
                it.fold(true) { acc: Boolean, b: Boolean? -> acc && (b ?: false) }
            }

    val toastSubject = BehaviorSubject.create<String>()

    val finishSubject = BehaviorSubject.create<Unit>()

    private val registerSubject = BehaviorSubject.create<Unit>()

    fun start(compositeDisposable: CompositeDisposable) {
        compositeDisposable.addAll(
            registerSubject.flatMap {
                listOf(emailSubject, passwordSubject, passwordCheckSubject)
                    .zip { RegisterItem(it[0], it[1], it[2]) }
            }
                .doOnNext {
                    if (it.password != it.passwordCheck) {
                        toastSubject.onNext("입력하신 비밀번호가 일치하지 않습니다.")
                    }
                }
                .filter { it.password == it.passwordCheck }
                .flatMapSingle {
                    registerRepository.register(it.email, it.password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { loadingSubject.onNext(true) }
                        .doAfterTerminate { loadingSubject.onNext(false) }
                }
                .subscribe {
                    if (it) {
                        toastSubject.onNext("회원가입 성공")
                        finishSubject.onNext(Unit)
                    } else {
                        toastSubject.onNext("알 수 없는 에러가 발생 했습니다.")
                    }
                }
        )
    }

    fun register() {
        registerSubject.onNext(Unit)
    }

}