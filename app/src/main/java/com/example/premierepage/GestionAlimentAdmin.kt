package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GestionAlimentAdmin : AppCompatActivity() {
    lateinit var ajouter : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_aliment_admin)
        ajouter = findViewById(R.id.ajouter)
        ajouter.setOnClickListener {
            val i = Intent(this,AjouterAlimentAdmin::class.java)
            startActivity(i)
        }
    }
}