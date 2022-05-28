package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_fifth.*
import kotlinx.android.synthetic.main.activity_personnel_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.HashMap

class personnelSetting : AppCompatActivity() {
    var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null

    lateinit var plus : ImageView
    lateinit var moins : ImageView
    var  num = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_setting)
        val modifier:Button=findViewById(R.id.modifier)
        plus = findViewById(R.id.plus)
        moins= findViewById(R.id.moins)
        val intent = intent
        var Poids = intent.getStringExtra("poids").toString().toDouble()
        val dec = DecimalFormat("#,###.00")
        var hauteur = intent.getStringExtra("hauteur").toString().toDouble()
        var age = intent.getStringExtra("age").toString().trim()
        var sexe = intent.getStringExtra("sexe").toString().trim()
        val poids:TextView=findViewById(R.id.poids)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)

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

        modifier.setOnClickListener {
            var token =myshared?.getString("token","")

            val map = HashMap<String?, String?>()
            map["poids"] = poids.text.toString()
            val call = retrofitInterface!!.executeSave(map,token)
            call!!.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        var editor: SharedPreferences.Editor=myshared!!.edit()
                        editor.putString("poids",poids.text.toString())
                        editor.commit()



                        Toast.makeText(this@personnelSetting, " success", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@personnelSetting,FifthActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else if (response.code() == 400) {
                        Toast.makeText(this@personnelSetting, "error", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(this@personnelSetting, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}