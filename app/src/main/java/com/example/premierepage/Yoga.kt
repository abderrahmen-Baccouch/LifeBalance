package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_yoga.*

class Yoga : AppCompatActivity() {
    private lateinit var nbrKcal : TextView
    private lateinit var duration : EditText
    private var nbr: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)
        val nomExerciceTV=findViewById<TextView>(R.id.nomExercice)
        val nomExercice = intent.getStringExtra("nomExercice")
        val calories = intent.getStringExtra("calories")?.toInt()

        nomExerciceTV.text=nomExercice

        nbrKcal = findViewById(R.id.kcal)
        duration = findViewById(R.id.duration)


        duration.addTextChangedListener{
            if(duration.text.isNotEmpty()) {
        val k = duration.text.toString().trim().toInt()
        nbrKcal.text = (calories!! * k).toString()
            }else{
                nbrKcal.text = "0"
            }

        }
        saveYoga.setOnClickListener {

            val intent = Intent(this,workoutGoal::class.java)
            val calerie = nbr.toString()
            intent.putExtra("nbrKcal10",calerie)
            Toast.makeText(this, calerie.toString(), Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}