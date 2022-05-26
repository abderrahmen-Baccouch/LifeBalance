package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.premierepage.model.Defit
import com.example.premierepage.view.Defit3Adapter
import kotlinx.android.synthetic.main.activity_burpees.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class burpees : AppCompatActivity() {
    private val START_TIME_IN_MILLIS: Long = 60000
    private var retrofitInterface: RetrofitInterface? = null
    private var mTextViewCountDown: TextView? = null
    private var mButtonStartPause: Button? = null
    private var mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burpees)
        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        var nomDefit:TextView=findViewById(R.id.nomDefit)
        val repetition:TextView=findViewById(R.id.repetition)
        val image2:ImageView=findViewById(R.id.image2)
        val image3:ImageView=findViewById(R.id.image3)
        val id:String=intent.getStringExtra("iddefit").toString()


        val call = retrofitInterface!!.executeGetDefit(id)
        call.enqueue(object : Callback<Defit> {
            override fun onResponse(call: Call<Defit>, response: Response<Defit>) {
                if (response.code()==200){
                    val defit=response.body()
                    nomDefit.text=defit!!.nomDefit
                    repetition.text=defit!!.repetition.toString()
                    Glide.with(this@burpees).load(defit!!.imageURL2).into(image2)
                    Glide.with(this@burpees).load(defit!!.imageURL3).into(image3)


                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<Defit>, t: Throwable) {
                Toast.makeText(this@burpees, t.message, Toast.LENGTH_LONG).show()
            }

        })

        mButtonStartPause!!.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        }

        updateCountDownText()
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
                updateCountDownText()
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