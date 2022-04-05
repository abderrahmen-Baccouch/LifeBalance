package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.varunest.sparkbutton.SparkButton

class Defit1 : AppCompatActivity() {
    private lateinit var button : SparkButton
    private lateinit var button2 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defit1)

        button = findViewById(R.id.sp_btn)
        button.setOnClickListener {
            val intent = Intent(this,LancerPremierDefit::class.java)
            startActivity(intent)
        }


    }

}