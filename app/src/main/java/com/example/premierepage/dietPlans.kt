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
            val intent = Intent(this,Breakfast1::class.java)
            startActivity(intent)
        }
        Breakfast2.setOnClickListener {
            val intent = Intent(this,breakfast2::class.java)
            startActivity(intent)
        }
        Breakfast3.setOnClickListener {
            val intent = Intent(this,breakfast3::class.java)
            startActivity(intent)
        }
        Breakfast1.setOnClickListener {
            val intent = Intent(this,Breakfast1::class.java)
            startActivity(intent)
        }
        Breakfast4.setOnClickListener {
            val intent = Intent(this,breakfast4::class.java)
            startActivity(intent)
        }
        Breakfast5.setOnClickListener {
            val intent = Intent(this,breakfast5::class.java)
            startActivity(intent)
        }
        Breakfast6.setOnClickListener {
            val intent = Intent(this,breakfast6::class.java)
            startActivity(intent)
        }
        Breakfast7.setOnClickListener {
            val intent = Intent(this,breakfast7::class.java)
            startActivity(intent)
        }
        Breakfast8.setOnClickListener {
            val intent = Intent(this,breakfast8::class.java)
            startActivity(intent)
        }
        Breakfast9.setOnClickListener {
            val intent = Intent(this,breakfast9::class.java)
            startActivity(intent)
        }
        Breakfast10.setOnClickListener {
            val intent = Intent(this,breakfast10::class.java)
            startActivity(intent)
        }
        Breakfast11.setOnClickListener {
            val intent = Intent(this,breakfast11::class.java)
            startActivity(intent)
        }
        Breakfast12.setOnClickListener {
            val intent = Intent(this,breakfast12::class.java)
            startActivity(intent)
        }
        Breakfast13.setOnClickListener {
            val intent = Intent(this,breakfast13::class.java)
            startActivity(intent)
        }
        Breakfast14.setOnClickListener {
            val intent = Intent(this,breakfast14::class.java)
            startActivity(intent)
        }
        Breakfast15.setOnClickListener {
            val intent = Intent(this,breakfast15::class.java)
            startActivity(intent)
        }
        Breakfast16.setOnClickListener {
            val intent = Intent(this,breakfast16::class.java)
            startActivity(intent)
        }
        Breakfast17.setOnClickListener {
            val intent = Intent(this,breakfast17::class.java)
            startActivity(intent)
        }
        Breakfast18.setOnClickListener {
            val intent = Intent(this,breakfast18::class.java)
            startActivity(intent)
        }
        Breakfast19.setOnClickListener {
            val intent = Intent(this,breakfast19::class.java)
            startActivity(intent)
        }
    }
}