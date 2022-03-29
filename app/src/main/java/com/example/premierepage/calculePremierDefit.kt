package com.example.premierepage


import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.spark.submitbutton.SubmitButton
import java.util.*


class calculePremierDefit : AppCompatActivity() {

/**
    private lateinit var countDownTimer: CountDownTimer
    private var buttonvalue: String? = null
    var startBtn: Button? = null
    var mtextview: TextView? = null
    private var MtimeRunning : Boolean = false
    private var MtimeLeftinmills: Long = 0

*/
    private val START_TIME_IN_MILLIS: Long = 60000

    private var mTextViewCountDown: TextView? = null
    private var mButtonStartPause: SubmitButton? = null
    private var mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcule_premier_defit)

        val intent = intent
        val buttonvalue = intent.getStringExtra("value")
        val intvalue = Integer.valueOf(buttonvalue)

        when (intvalue) {
            1  ->  setContentView(R.layout.activity_bow)
            2  ->  setContentView(R.layout.activity_bridge)
            3  ->  setContentView(R.layout.activity_chair)
            4  ->  setContentView(R.layout.activity_child)
            5  ->  setContentView(R.layout.activity_cobbler)
            6  ->  setContentView(R.layout.activity_cow)
            7  ->  setContentView(R.layout.activity_play_ji)
            8  ->  setContentView(R.layout.activity_pauseji)
            9  ->  setContentView(R.layout.activity_plank)
            10  ->  setContentView(R.layout.activity_crunches)
            11  ->  setContentView(R.layout.activity_situp)
            12  ->  setContentView(R.layout.activity_rotation)
            13  ->  setContentView(R.layout.activity_twist)
            14  ->  setContentView(R.layout.activity_windmill)
            15  ->  setContentView(R.layout.activity_logup)

        }

       /** startBtn = findViewById(R.id.startbutton)
        mtextview = findViewById(R.id.time)
*/
        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)
        mButtonReset = findViewById(R.id.button_reset)


        mButtonStartPause!!.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        }
        mButtonReset!!.setOnClickListener(View.OnClickListener { resetTimer() })
        updateCountDownText()
/**  startBtn!!.setOnClickListener {

            if (MtimeRunning){
                stoptimer()

            }
            else {
                startTimer()
            }


        }
*/

    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                mButtonStartPause!!.text = "Start"
                mButtonStartPause!!.visibility = View.INVISIBLE
                mButtonReset!!.visibility = View.VISIBLE
            }
        }.start()
        mTimerRunning = true
       mButtonStartPause!!.text = "pause"
        mButtonReset!!.visibility = View.INVISIBLE
    }


    private fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
      mButtonStartPause!!.text = "Start"
        mButtonReset!!.visibility = View.VISIBLE
    }
    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        mButtonReset!!.visibility = View.INVISIBLE
        mButtonStartPause!!.visibility = View.VISIBLE
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        mTextViewCountDown!!.text = timeLeftFormatted
    }



/**
    private fun stoptimer() {
        countDownTimer.cancel()
        MtimeRunning = false
        startBtn!!.text = "START"
    }

    private fun startTimer() {
        val value1 = mtextview!!.text
        val num1 = value1.toString()
        val num2 = num1.substring(0,2)
        val num3 = num1.substring(3,5)

        val number: Int = Integer.valueOf(num2) * 60 + Integer.valueOf(num3)
        MtimeLeftinmills = (number*1000).toLong()

        countDownTimer = object : CountDownTimer(MtimeLeftinmills, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                MtimeLeftinmills = millisUntilFinished
                updateTimer()

            }

            override fun onFinish() {

                var newvalue: Int = Integer.valueOf(buttonvalue) + 1
                if (newvalue <= 7) {

                    val intent = Intent(this@calculePremierDefit, calculePremierDefit::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.putExtra("value", newvalue.toString())
                    startActivity(intent)
                } else {
                    newvalue = 1
                    val intent = Intent(this@calculePremierDefit, calculePremierDefit::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.putExtra("value", newvalue.toString())
                    startActivity(intent)
                }
            }
        }.start()
        startBtn!!.text = "PAUSE"
        MtimeRunning = true


    }
    private fun updateTimer() {
        val minutes = MtimeLeftinmills.toInt() / 60000
        val seconds = MtimeLeftinmills.toInt() % 60000 / 1000
        var timeLeftText = ""
        if (minutes < 10)
            timeLeftText = "0"
        timeLeftText = "${timeLeftText+minutes} :"
        if (seconds < 10)
            timeLeftText += "0"
        timeLeftText += seconds
        mtextview!!.text = timeLeftText
    }

    override fun onBackPressed() {
        super.onBackPressed() }
    */
}