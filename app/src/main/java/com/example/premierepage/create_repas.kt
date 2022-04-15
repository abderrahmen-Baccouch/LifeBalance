package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class create_repas : AppCompatActivity() {
    private lateinit var Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_repas)
        Btn = findViewById(R.id.cancel)
        Btn.setOnClickListener {
            val intent = Intent(this,FoodFragment::class.java)
            startActivity(intent)
        }
    }

}