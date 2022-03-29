package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercices.*

class ActivityExercices : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercices)


        myCustomActivity.setOnClickListener {
            val intent =Intent(this,customActivity::class.java)
            startActivity(intent)
        }

    }
}