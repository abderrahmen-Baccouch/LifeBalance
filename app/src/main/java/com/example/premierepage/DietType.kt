package com.example.premierepage


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

class DietType : AppCompatActivity() {

    private lateinit var app1 : TextView
    private lateinit var app2 : TextView
    private lateinit var app3 : TextView
    private lateinit var app4: TextView
    private lateinit var retour : GifImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_type)
        app1 = findViewById(R.id.apply1)
        app2 = findViewById(R.id.apply2)
        app3 = findViewById(R.id.apply3)
        app4 = findViewById(R.id.apply4)
        retour = findViewById(R.id.retour)



        retour.setOnClickListener {
            val intent = Intent(this,FifthActivity::class.java)
            startActivity(intent)
        }
        app1.setOnClickListener {
            val intent = Intent(this,balancedDiet::class.java)
            startActivity(intent)
        }
        app2.setOnClickListener {
            val intent = Intent(this,hightProteinDiet::class.java)
            startActivity(intent)
        }
        app3.setOnClickListener {
            val intent = Intent(this,keto_easy::class.java)
            startActivity(intent)
        }
        app4.setOnClickListener {
            val intent = Intent(this,keto_medium::class.java)
            startActivity(intent)
        }
    }
}