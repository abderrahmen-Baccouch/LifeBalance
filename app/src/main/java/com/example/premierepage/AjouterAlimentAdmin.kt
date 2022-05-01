package com.example.premierepage

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AjouterAlimentAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_aliment_admin)
        /**-------------------------------------retrofit-------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        /**-------------------------------------myshared-------------------------------------------------- */
        myshared=getSharedPreferences("myshared",0)




        val nomAlimentET = findViewById<EditText>(R.id.nomAliment)
        val caloriesET = findViewById<EditText>(R.id.calories)
        val proteinesET = findViewById<EditText>(R.id.proteines)
        val glucidesET = findViewById<EditText>(R.id.glucides)
        val lipidesET = findViewById<EditText>(R.id.lipides)
        val save_aliment= findViewById<Button>(R.id.save_aliment)


        save_aliment.setOnClickListener {

            val nomAliment = nomAlimentET.text.toString()
            val calories = caloriesET.text.toString()
            val proteines = proteinesET.text.toString()
            val glucides = glucidesET.text.toString()
            val lipides = lipidesET.text.toString()

            val map = HashMap<String?, String?>()
            map["nomAliment"] = nomAliment
            map["calories"] = calories
            map["proteines"] = proteines
            map["glucides"] = glucides
            map["lipides"] = lipides

            val call = retrofitInterface!!.executeAddNotreAliment(map)
            call!!.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        Toast.makeText(this@AjouterAlimentAdmin, "aliment ajouté", Toast.LENGTH_LONG).show()
                        finish()

                    } else if (response.code() == 401) {
                        Toast.makeText(this@AjouterAlimentAdmin, "aliment existe", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        Toast.makeText(this@AjouterAlimentAdmin, "an error occured while saving aliment", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void?>?, t: Throwable) {
                    Toast.makeText(this@AjouterAlimentAdmin, t.message, Toast.LENGTH_LONG).show()
                }
            })


        }


    }
}