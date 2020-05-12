package com.dino.rxsample

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dino.rxsample.week5.behaviorsubject.BehaviorSubjectActivity
import com.dino.rxsample.week5.buffer.BufferActivity
import com.dino.rxsample.week5.combinelatest.CombineLatestActivity
import com.dino.rxsample.week5.debounce.DebounceActivity
import com.dino.rxsample.week5.throttle.ThrottleActivity
import com.dino.rxsample.week6.NaverMovieSearchActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    fun debounce(v: View) {
        val intent = Intent(this, DebounceActivity::class.java)
        startActivity(intent)
    }

    fun throttle(v: View) {
        val intent = Intent(this, ThrottleActivity::class.java)
        startActivity(intent)
    }

    fun buffer(v: View) {
        val intent = Intent(this, BufferActivity::class.java)
        startActivity(intent)
    }

    fun combineLatest(v: View) {
        val intent = Intent(this, CombineLatestActivity::class.java)
        startActivity(intent)
    }

    fun behaviorSubject(v: View) {
        val intent = Intent(this, BehaviorSubjectActivity::class.java)
        startActivity(intent)
    }

    fun rxMvvm(v: View) {
        val intent = Intent(this, NaverMovieSearchActivity::class.java)
        startActivity(intent)
    }

}
