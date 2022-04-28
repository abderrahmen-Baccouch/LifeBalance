package com.example.premierepage

import android.content.Intent
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

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        quantite = findViewById(R.id.quantite)
        saveQuantite= findViewById(R.id.saveQuantite)
        cancelQuantite= findViewById(R.id.cancelQuantite)

        /**bech njibou token mel fichier myshared */
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        var idrecette =myshared?.getString("idrecette","")
        var idaliment=intent.getStringExtra("idaliment")
        Toast.makeText(this@QuantiteAliment, "iaa", Toast.LENGTH_LONG).show()

        saveQuantite.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["quantite"] = quantite.text.toString()
            Toast.makeText(this@QuantiteAliment, "bb", Toast.LENGTH_LONG).show()
            val call = retrofitInterface!!.executeAddIngredient(token,idrecette,idaliment,map)

            call.enqueue(object : Callback<RecetteX> {
                override fun onResponse(call: Call<RecetteX>, response: Response<RecetteX>) {
                    if (response.code()==200){
                        Toast.makeText(this@QuantiteAliment, "ingredient ajouté", Toast.LENGTH_LONG).show()
                        finish()
                    }else if (response.code()==400){
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