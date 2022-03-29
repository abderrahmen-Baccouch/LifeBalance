package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Defit : AppCompatActivity() {
    private lateinit var button1 : Button
    private lateinit var button2 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defit)

        setSupportActionBar(findViewById((R.id.toolBar)))

        button1 = findViewById(R.id.startexercice1)
        button2 = findViewById(R.id.startexercice2)

        button1.setOnClickListener {
            val intent = Intent(this,LancerPremierDefit::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener {
            val intent = Intent(this,LancerDeuxiemeDefit::class.java)
            startActivity(intent)
        }


    }

    fun beforeage18(view: View) {
        val intent = Intent(this,LancerPremierDefit::class.java)
        startActivity(intent)
    }
    fun aftereage18(view: View) {
        val intent = Intent(this,LancerDeuxiemeDefit::class.java)
        startActivity(intent)
    }
    fun food(view: View) {
        val intent = Intent(this,FoodActivity::class.java)
        startActivity(intent)
    }
}