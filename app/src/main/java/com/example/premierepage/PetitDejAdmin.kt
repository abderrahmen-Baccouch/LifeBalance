package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PetitDejAdmin : AppCompatActivity() {
    lateinit var ajouter : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petit_dej_admin)
        ajouter= findViewById(R.id.ajouter)
        ajouter.setOnClickListener {
            val i = Intent(this,AjouterRepasAdmin::class.java)
            startActivity(i)
        }

    }
}