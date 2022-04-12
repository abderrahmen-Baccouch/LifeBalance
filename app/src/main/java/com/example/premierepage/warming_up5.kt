package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class warming_up5 : AppCompatActivity() {
    lateinit var btn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warming_up5)
        btn = findViewById(R.id.button)
        btn.setOnClickListener {
            val intent = Intent(this,legRaise::class.java)
            startActivity(intent)
        }
    }
}