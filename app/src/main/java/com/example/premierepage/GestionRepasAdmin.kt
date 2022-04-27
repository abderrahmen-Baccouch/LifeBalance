package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_diet_plans0.*

class GestionRepasAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_repas_admin)
        petit_dejeuner.setOnClickListener {
            val intent = Intent(this,PetitDejAdmin::class.java)
            startActivity(intent)
        }
        dejeuner.setOnClickListener {
            val intent = Intent(this, DejeunerAdmin::class.java)
            startActivity(intent)
        }
        dinner.setOnClickListener {
            val intent = Intent(this, DinnerAdmin::class.java)
            startActivity(intent)
        }


    }
}