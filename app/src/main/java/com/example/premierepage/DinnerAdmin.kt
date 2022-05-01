package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DinnerAdmin : AppCompatActivity() {
    lateinit var ajouter : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner_admin)
        ajouter= findViewById(R.id.ajouter_diner)
        ajouter.setOnClickListener {
            val i = Intent(this, AjouterRepasAdmin::class.java)
            startActivity(i)
        }
    }
}