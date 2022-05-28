package com.example.premierepage


import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.RecetteX
import com.example.premierepage.view.AlimentAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuantiteAliment : AppCompatActivity() {
    private lateinit var quantite : EditText
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    private lateinit var saveQuantite : Button
    private lateinit var cancelQuantite : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quantite_aliment)
        /*********************** Retrofit and myshared and intent  *************************************************/
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        var role =myshared?.getString("role","")
        var idrecette =myshared?.getString("idrecette","")
        var idaliment=intent.getStringExtra("idaliment")

        quantite = findViewById(R.id.quantite)
        saveQuantite= findViewById(R.id.saveQuantite)
        cancelQuantite= findViewById(R.id.cancelQuantite)

        cancelQuantite.setOnClickListener {
            finish()
        }
        saveQuantite.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["quantite"] = quantite.text.toString()

            if (role!="1"){
                val call = retrofitInterface!!.executeAddIngredient(token, idrecette, idaliment, map)
                call.enqueue(object : Callback<RecetteX> {
                    override fun onResponse(call: Call<RecetteX>, response: Response<RecetteX>) {
                        if (response.code() == 200) {
                            finish()
                        } else if (response.code() == 400) {
                            Toast.makeText(this@QuantiteAliment, "400", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RecetteX>, t: Throwable) {
                        Toast.makeText(this@QuantiteAliment, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }else{
                val call = retrofitInterface!!.executeAddIngredientAdmin(idrecette, idaliment, map)
                call.enqueue(object : Callback<RecetteX> {
                    override fun onResponse(call: Call<RecetteX>, response: Response<RecetteX>) {
                        if (response.code() == 200) {
                            finish()
                        } else if (response.code() == 400) {
                            Toast.makeText(this@QuantiteAliment, "400", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RecetteX>, t: Throwable) {
                        Toast.makeText(this@QuantiteAliment, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

    }
}