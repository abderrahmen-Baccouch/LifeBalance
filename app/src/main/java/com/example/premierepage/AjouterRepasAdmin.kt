package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AjouterRepasAdmin : AppCompatActivity() {
    lateinit var bouton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_repas_admin)
        bouton = findViewById(R.id.bouton)
        bouton.setOnClickListener {
            val intent = Intent(this,Formulaire_Repas_Admin::class.java)
            startActivity(intent)
        }
    }
}