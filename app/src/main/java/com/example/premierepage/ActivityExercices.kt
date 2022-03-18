package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercices.*

class ActivityExercices : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercices)

        bicycling.setOnClickListener {
            val intent =Intent(this,calculeKcalActivity::class.java)
            startActivity(intent)
        }
        running.setOnClickListener {
            val intent =Intent(this,Running::class.java)
            startActivity(intent)
        }
        walking.setOnClickListener {
            val intent =Intent(this,Walking::class.java)
            startActivity(intent)
        }
        abdominal.setOnClickListener {
            val intent =Intent(this,Abdominal::class.java)
            startActivity(intent)
        }
        aerobic.setOnClickListener {
            val intent =Intent(this,Aerobic::class.java)
            startActivity(intent)
        }
        football.setOnClickListener {
            val intent =Intent(this,Football::class.java)
            startActivity(intent)
        }
        tennis.setOnClickListener {
            val intent =Intent(this,Tennis::class.java)
            startActivity(intent)
        }
        boxing.setOnClickListener {
            val intent =Intent(this,Boxing::class.java)
            startActivity(intent)
        }
        skating.setOnClickListener {
            val intent =Intent(this,Skating::class.java)
            startActivity(intent)
        }
        yoga.setOnClickListener {
            val intent =Intent(this,Yoga::class.java)
            startActivity(intent)
        }
        myCustomActivity.setOnClickListener {
            val intent =Intent(this,customActivity::class.java)
            startActivity(intent)
        }

    }
}