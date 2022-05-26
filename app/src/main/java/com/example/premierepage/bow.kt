package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.premierepage.model.Defit
import com.example.premierepage.view.DefitAdapter
import com.example.premierepage.view.StepAdapter
import com.spark.submitbutton.SubmitButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class bow : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null

    var myshared: SharedPreferences?=null
    private lateinit var recv: RecyclerView
   private val START_TIME_IN_MILLIS: Long = 60000

    private var mTextViewCountDown: TextView? = null
    private var mButtonStartPause: SubmitButton? = null
    private var mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bow)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.listeStepRecycler)
        var imageURL=intent.getStringExtra("imageURL")
        var imageDefitIV=findViewById<ImageView>(R.id.imageDefit2)
        Glide.with(this).load(imageURL).into(imageDefitIV)

        getSteps()
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
        mButtonStartPause!!.text = "Start"
        mButtonReset!!.visibility = View.INVISIBLE
    }


    private fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
        mButtonStartPause!!.text = "pause"
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

    fun getSteps(){
        //5edmet l affichage mte3 les aliments
        var iddefit=intent.getStringExtra("iddefit")
        val call = retrofitInterface!!.executeAllEtapes(iddefit)
        call.enqueue(object : Callback<MutableList<String>> {
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {
                if (response.code()==200){
                    val listDefit=response.body()
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@bow)
                        adapter= StepAdapter(this@bow,response.body()!!)
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Toast.makeText(this@bow, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}