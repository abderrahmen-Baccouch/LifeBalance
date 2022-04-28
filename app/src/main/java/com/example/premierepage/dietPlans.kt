package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.premierepage.Breakfast1
import kotlinx.android.synthetic.main.activity_diet_plans.*

class dietPlans : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plans)
        Breakfast1.setOnClickListener {
            val intent = Intent(this,com.example.premierepage.Breakfast1::class.java)
            startActivity(intent)
        }

    }
}