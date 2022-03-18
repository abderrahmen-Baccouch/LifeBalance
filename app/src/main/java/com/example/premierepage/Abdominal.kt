package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_abdominal.*

class Abdominal : AppCompatActivity() {
    private lateinit var nbrKcal : TextView
    private lateinit var minute : TextView
    private var nbr: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abdominal)

        nbrKcal = findViewById(R.id.kcal)
        minute = findViewById(R.id.duration)

        minute.addTextChangedListener{
            val k = minute.text.toString().trim()
            nbrKcal.text = (4 * k.toInt()).toString()
            nbr = nbrKcal.text.toString()
        }
        saveAbdo.setOnClickListener {

            val intent = Intent(this,workoutGoal::class.java)
            val calerie = nbr.toString()
            val name2 = "abdo"
            intent.putExtra("nbrKcal2",calerie)
            intent.putExtra("name2",name2)
            Toast.makeText(this, calerie.toString(), Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}