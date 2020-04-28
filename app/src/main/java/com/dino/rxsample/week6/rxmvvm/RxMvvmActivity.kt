package com.dino.rxsample.week6.rxmvvm

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dino.rxsample.R
import com.dino.rxsample.week6.rxmvvm.data.RegisterRepositoryImpl
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_rx_mvvm.*
import java.util.concurrent.TimeUnit

class RxMvvmActivity : AppCompatActivity(R.layout.activity_rx_mvvm) {

    private val compositeDisposable = CompositeDisposable()

    private val viewModel = RxMvvmViewModel(RegisterRepositoryImpl())

    override fun onStart() {
        super.onStart()
        compositeDisposable.addAll(
            RxTextView.textChangeEvents(et_email)
                .map { it.text().toString() }
                .subscribe { viewModel.emailSubject.onNext(it) },
            RxTextView.textChangeEvents(et_password)
                .map { it.text().toString() }
                .subscribe { viewModel.passwordSubject.onNext(it) },
            RxTextView.textChangeEvents(et_password_check)
                .map { it.text().toString() }
                .subscribe { viewModel.passwordCheckSubject.onNext(it) },
            RxView.clicks(btn_register)
                .throttleFirst(1000L, TimeUnit.MILLISECONDS)
                .subscribe { viewModel.register() },
            viewModel.registerEnableSubject
                .subscribe { btn_register.isEnabled = it },
            viewModel.toastSubject
                .subscribe { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() },
            viewModel.loadingSubject
                .subscribe { pb_loading.isVisible = it },
            viewModel.finishSubject
                .subscribe { finish() }
        )
        viewModel.start(compositeDisposable)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }


}