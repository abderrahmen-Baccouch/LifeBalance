package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Breakfast1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast1)
        var nomRepasTV: TextView =findViewById<TextView>(R.id.nomRepas)
        var caloriesTV: TextView =findViewById<TextView>(R.id.calories)

        var nomRepas=intent.getStringExtra("nomRepas")
        var calories=intent.getIntExtra("calories",0)

        nomRepasTV.text=nomRepas
        caloriesTV.text=calories.toString()
    }













}