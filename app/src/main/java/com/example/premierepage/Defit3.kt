package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_defit3.*

class Defit3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defit3)

        skills1.setOnClickListener {
            val intent = Intent(this,warming_up2::class.java)
            startActivity(intent)
        }
        skills2.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }
        skills3.setOnClickListener {
            val intent = Intent(this,warming_up3::class.java)
            startActivity(intent)
        }
        skills4.setOnClickListener {
            val intent = Intent(this,warming_up4::class.java)
            startActivity(intent)
        }
        skills5.setOnClickListener {
            val intent = Intent(this,warming_up5::class.java)
            startActivity(intent)
        }
       /*




        skills_pro1.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }
        skills_pro2.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }
        skills_pro3.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }
        skills_pro4.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }
        skills_pro5.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }
        skills_pro6.setOnClickListener {
            val intent = Intent(this,warming_up::class.java)
            startActivity(intent)
        }*/

    }
}