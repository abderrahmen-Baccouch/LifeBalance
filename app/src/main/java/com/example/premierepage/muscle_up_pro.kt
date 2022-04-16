package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import java.util.*

class muscle_up_pro : AppCompatActivity() {
    private val START_TIME_IN_MILLIS: Long = 60000
    private var mTextViewCountDown: TextView? = null
    private var mButtonStartPause: Button? = null
    private var mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscle_up_pro)
        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)

        mButtonStartPause!!.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        }
    }
    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                mButtonStartPause!!.text = "Done"
                mButtonStartPause!!.setOnClickListener {
                    mTimeLeftInMillis = START_TIME_IN_MILLIS
                    mButtonStartPause!!.text = "Start"

                }
                // mButtonStartPause!!.visibility = View.INVISIBLE

            }
        }.start()
        mTimerRunning = true
        mButtonStartPause!!.text = "Pause"

    }
    private fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
        mButtonStartPause!!.text = "Start"

    }
    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        mTextViewCountDown!!.text = timeLeftFormatted
    }
}