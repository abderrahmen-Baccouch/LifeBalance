package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_yoga.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Yoga : AppCompatActivity() {
    private lateinit var nbrKcal : TextView
    private lateinit var duration : EditText
    private var nbr: String? = null
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)

        val nomExerciceTV=findViewById<TextView>(R.id.nomExercice)
        val nomExercice = intent.getStringExtra("nomExercice")
        val imageURL = intent.getStringExtra("imageURL")
        val calories = intent.getStringExtra("calories")?.toDouble()
        val id = intent.getStringExtra("id")

        nomExerciceTV.text=nomExercice

        nbrKcal = findViewById(R.id.kcal)
        duration = findViewById(R.id.duration)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")

        duration.addTextChangedListener{
            if(duration.text.isNotEmpty()) {
                val k = duration.text.toString().trim().toInt()
                val x=((calories!!/30) * k).toInt()
                nbrKcal.text = x.toString()
            }else{
                nbrKcal.text = "0"
            }

        }

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        saveYoga.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["nomExercice"] = nomExercice
            map["imageURL"] = imageURL
            map["duration"]=duration.text.toString()
            map["calories"]=nbrKcal.text.toString()

            val call = retrofitInterface!!.executeAddExerciceTermine(token,map)
            call?.enqueue(object :retrofit2.Callback<Void?>{
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code()==200){
                        Toast.makeText(this@Yoga,"exercicetermine ajoute",Toast.LENGTH_LONG).show()
                    }
                    else if (response.code()==400){
                        Toast.makeText(this@Yoga,"errrrror",Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(this@Yoga, t.message, Toast.LENGTH_LONG).show()
                }

            })




            val intent = Intent(this,workoutGoal::class.java)
            val calerie = nbr.toString()
            intent.putExtra("nbrKcal10",calerie)
            startActivity(intent)
        }
    }
}