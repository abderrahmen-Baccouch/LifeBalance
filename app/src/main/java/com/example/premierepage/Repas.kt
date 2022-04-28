package com.example.premierepage

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.premierepage.model.BreakfastX
import retrofit2.Call
import retrofit2.Response

class Repas : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repas)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        myshared=getSharedPreferences("myshared",0)

        var nomRecetteTV: TextView =findViewById<TextView>(R.id.nomRecette)
        var nomRecette=intent.getStringExtra("nomRecette")
        var idRecette=intent.getStringExtra("idRecette")
        nomRecetteTV.text=nomRecette

        val token =myshared?.getString("token","").toString().trim()
        val idbreakfast=myshared?.getString("idbreakfast","").toString().trim()

        var addBreakFast:Button=findViewById(R.id.addBreakFast)
        var addDejeuner:Button=findViewById(R.id.addDejeuner)
        var addDinner:Button=findViewById(R.id.addDinner)


        addBreakFast.setOnClickListener {
            val call = retrofitInterface!!.executeAddBreakfast(token,idbreakfast,idRecette)
            call.enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@Repas,response.code().toString(),Toast.LENGTH_LONG).show()
                    if (response.code()==200){
                        Toast.makeText(this@Repas,"ok",Toast.LENGTH_LONG).show()
                    }else if (response.code()==400){
                        Toast.makeText(this@Repas,"400",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@Repas, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }

        addDejeuner.setOnClickListener {

        }

        addDinner.setOnClickListener {

        }








    }
}