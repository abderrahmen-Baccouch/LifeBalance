package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DejeunerAdmin : AppCompatActivity() {
    lateinit var ajouter : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dejeuner_admin)
        ajouter= findViewById(R.id.ajouter_dejeuner)
        ajouter.setOnClickListener {
            val i = Intent(this, AjouterRepasAdmin::class.java)
            startActivity(i)
        }

    }
}