package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class warming_up4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var btn : Button
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warming_up4)
        btn = findViewById(R.id.button)
        btn.setOnClickListener {
            val intent = Intent(this,dips::class.java)
            startActivity(intent)
        }
    }
}