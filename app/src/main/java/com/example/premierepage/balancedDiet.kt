package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class balancedDiet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var out : ImageView
        lateinit var activer : Button
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balanced_diet)
        out= findViewById(R.id.out)
        activer= findViewById(R.id.activer)
        activer.setOnClickListener {
            val intent = Intent(this,diaryBalanceActivity::class.java)
            startActivity(intent)
        }
        out.setOnClickListener {
            val intent = Intent(this,DietType::class.java)
            startActivity(intent)
        }
    }
}