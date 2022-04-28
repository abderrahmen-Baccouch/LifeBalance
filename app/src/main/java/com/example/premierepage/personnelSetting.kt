package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_fifth.*
import kotlinx.android.synthetic.main.activity_personnel_setting.*
import java.text.DecimalFormat

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
        var Poids = intent.getStringExtra("poids").toString().toDouble()
        val dec = DecimalFormat("#,###.00")
        var hauteur = intent.getStringExtra("hauteur").toString().toDouble()
        var age = intent.getStringExtra("age").toString().trim()
        var sexe = intent.getStringExtra("sexe").toString().trim()



        num = Poids!!.toInt().toDouble()
        poids.text = num.toString()

        plus.setOnClickListener {
            num=num+0.5
            poids.text = num.toString()
        }
        moins.setOnClickListener {
            num=num-0.5
            poids.text = num.toString()
        }
        poids2.text = num.toString()

        imc2.text = dec.format(Poids/(hauteur*hauteur)).toString()
        poids.addTextChangedListener{
            poids2.text = num.toString()
        }
        poids.addTextChangedListener{
            imc2.text = dec.format(num/(hauteur*hauteur)).toString()

        }

      /*  poids.addTextChangedListener{
            img.text  =  dec.format(1.20 * a).toString()
            /*val b =  dec.format(0.23 * age.toDouble()).toString()
            val c = -16.2
            val d = a.toDouble()+b.toDouble()+c*/
            /*
            else if (sexe == "FEMME"){
                img.text =  dec.format((1.20 * imc2.toString().toDouble()+(0.23 * age.toDouble())-5.4)).toString()
            }*/

        }*/
    }
}