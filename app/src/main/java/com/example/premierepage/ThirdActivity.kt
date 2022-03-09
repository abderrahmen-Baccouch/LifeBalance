package com.example.premierepage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_third.*
import java.util.*

class ThirdActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener{


    private var cYear: Int? = null
    private var cAge: Int? = null
    private lateinit var etHauteur : EditText
    private lateinit var etPoids : EditText
    private lateinit var etage : TextView


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)


        //get data from intent
        val intent = intent
        val username = intent.getStringExtra("username")

        // textView
        val resultview = findViewById<TextView>(R.id.username)
        //setText
        resultview.text = username


        etHauteur = findViewById(R.id.Hauteur)
        etPoids = findViewById(R.id.Poids)
        etage = findViewById(R.id.age)

        valider.setOnClickListener{

            val hauteur = etHauteur.text.toString().trim()
            val poids = etPoids.text.toString().trim()
            val age = etage.text.toString().trim()
            val cAge = cAge.toString().trim()
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            radioGroup.setOnCheckedChangeListener{ group,checkedId ->
                if (checkedId == R.id.radio1)
                    Toast.makeText(this,radio1.text.toString(), Toast.LENGTH_SHORT).show()
                if (checkedId == R.id.radio2)
                    Toast.makeText(this,radio2.text.toString(), Toast.LENGTH_SHORT).show()
                if (radioGroup ==null) {
                    radio1.error = "il faut choisir"
                    radio2.error = "il faut choisir"
                }

            }

            if (age.format(Date()).isEmpty()){
                etage.error="age est obligatoire";
                Toast.makeText(this, "Age est Obligatoire!", Toast.LENGTH_SHORT).show()}
            else if((currentYear - cYear!!)<15){
                etage.error="age est invalide";
                Toast.makeText(this, "Age minimale : 15 ans !", Toast.LENGTH_SHORT).show()
            }
            else if(hauteur.isEmpty())  {
                etHauteur.error = "Hauteur est obligatoire";
                Toast.makeText(this, "Hauteur est obligatoire !", Toast.LENGTH_SHORT).show()}
            else if (hauteur.toFloat()<1 || hauteur.toFloat()>3){
                etHauteur.error ="Hauteur est Invalide"
                Toast.makeText(this, "Hauteur est Invalide !", Toast.LENGTH_SHORT).show()
            }
            else if(poids.isEmpty() ) {
                etPoids.error = "Poids est obligatoire";
                Toast.makeText(this, "Poids est obligatoire !", Toast.LENGTH_SHORT).show()}
            else if (poids.toFloat()>200 ||poids.toFloat()<10){
                etPoids.error = "Poids est Invalide";
                Toast.makeText(this, "Poids est Invalide !", Toast.LENGTH_SHORT).show()}

            else {
                Toast.makeText(this, "Informations Collectées !", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,FifthActivity::class.java)
                intent.putExtra("poids",poids)
                intent.putExtra("hauteur",hauteur)
                intent.putExtra("age",cAge)
                startActivity(intent)
            }

        }


        //Calendar
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        //button click to show DatePicker
        buttonDatePicker.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener
            { view, mYear, mMonth, mDay ->
                day = mDay
                month = mMonth
                year = mYear
                //set to textView
                age.text= "$day/${month+1}/$year"
                cYear=year
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                cAge = currentYear - year
            },year,month,day)

            //Show dialog
            dpd.show()
        }

    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }



}
