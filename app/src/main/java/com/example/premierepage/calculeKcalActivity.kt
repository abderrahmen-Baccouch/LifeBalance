package com.example.premierepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_calculekcal.*

class calculeKcalActivity : AppCompatActivity() {

    private lateinit var nbrKcal : TextView
    private lateinit var minute : TextView
    private var nbr: String? = null
    private var name: String? = null
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculekcal)

        nbrKcal = findViewById(R.id.kcalBicycling)
        minute = findViewById(R.id.duration)


        minute.addTextChangedListener{

            val k = minute.text.toString().trim()
            nbrKcal.text = (9 * k.toInt()).toString()
            nbr = nbrKcal.text.toString()
        }
        saveBicycling.setOnClickListener {



            val intent = Intent(this,workoutGoal::class.java)
            val calerie = nbr.toString().toInt()
            val n = "Bicycling"

            intent.putExtra("nbrKcal1",calerie)
            intent.putExtra("name",n)

            Toast.makeText(this, calerie.toString(), Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }

    }
}