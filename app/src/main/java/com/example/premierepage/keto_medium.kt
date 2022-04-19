package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class keto_medium : AppCompatActivity() {
    private lateinit var out : ImageView
    lateinit var activer : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keto_medium)
        out = findViewById(R.id.out)
        activer= findViewById(R.id.activer)
        activer.setOnClickListener {
            val intent = Intent(this,dietPlans::class.java)
            startActivity(intent)
        }
        out.setOnClickListener {
            val intent = Intent(this,DietType::class.java)
            startActivity(intent)
        }
    }
}