package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_skating.*

class Skating : AppCompatActivity() {
    private lateinit var nbrKcal : TextView
    private lateinit var minute : TextView
    private var nbr: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skating)

        nbrKcal = findViewById(R.id.kcal)
        minute = findViewById(R.id.duration)

        minute.addTextChangedListener{

            val k = minute.text.toString().trim()
            nbrKcal.text = (8 * k.toInt()).toString()
            nbr = nbrKcal.text.toString()
        }
        saveSkating.setOnClickListener {

            val intent = Intent(this,workoutGoal::class.java)
            val calerie = nbr.toString()
            intent.putExtra("nbrKcal7",calerie)
            Toast.makeText(this, calerie.toString(), Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}