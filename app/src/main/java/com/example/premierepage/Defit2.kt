package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.varunest.sparkbutton.SparkButton

class Defit2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var button : SparkButton
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defit2)

        button = findViewById(R.id.sp_btn)
        button.setOnClickListener {
            val intent = Intent(this,LancerDeuxiemeDefit::class.java)
            startActivity(intent)
        }
    }
}