package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_personnel_setting.*

class personnelSetting : AppCompatActivity() {
    lateinit var plus : ImageView
    lateinit var moins : ImageView
    var  num = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_setting)

        plus = findViewById(R.id.plus)
        moins= findViewById(R.id.moins)
        val intent = intent
        var cAge = intent.getStringExtra("poids")
        num= cAge!!.toInt().toDouble()
        poids.text = num.toString()

        plus.setOnClickListener {
            num=num+0.5
            poids.text = num.toString()
        }
        moins.setOnClickListener {
            num=num-0.5
            poids.text = num.toString()
        }

    }
}