package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.premierepage.model.Recette
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AjouterRepasAdmin : AppCompatActivity() {
    lateinit var addIngredient: Button
    var myshared: SharedPreferences?=null
    private var retrofitInterface: RetrofitInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_repas_admin)

        /********************************** Retrofit and myshared************************** */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        var role =myshared?.getString("role","")

        val typeRepas=intent.getStringExtra("typeRepas")



        /**.................................   .findViewById.   ...............................................*/
        val nomRepasET:EditText = findViewById(R.id.nomRepas)
        val  tempsPreparationET:EditText = findViewById(R.id. tempsPreparation)
        val saveRecette:Button=findViewById(R.id.saveRecette)
        val cancelRecette:Button=findViewById(R.id.cancelRecette)
        addIngredient = findViewById(R.id.addIngredient)
        addIngredient.setOnClickListener {
            val i=Intent(this@AjouterRepasAdmin,ListeAliments::class.java)
            if (myshared?.getString("idrecette","")==""){
                /**5edmet l back*/
                val map = HashMap<String?, String?>()
                map["nomRecette"] = nomRepasET.text.toString()
                map["temps"] = tempsPreparationET.text.toString()
                map["typeRepas"]=typeRepas.toString()
                if(role!="1") {
                    val call = retrofitInterface!!.executeAddRecette(map, token)
                    call.enqueue(object : Callback<Recette> {
                        override fun onResponse(call: Call<Recette>, response: Response<Recette>) {
                            if (response.code() == 200) {
                                var editor: SharedPreferences.Editor = myshared!!.edit()
                                editor.putString("idrecette", response.body()!!.recette._id.toString())
                                editor.putString("nomRecette", response.body()!!.recette.nomRecette)
                                editor.commit()
                                startActivity(i)
                            } else if (response.code() == 400) {
                                Toast.makeText(this@AjouterRepasAdmin, "400", Toast.LENGTH_LONG).show()
                            } else if (response.code() == 401) {
                                Toast.makeText(this@AjouterRepasAdmin, "401", Toast.LENGTH_LONG).show()
                            }
                        }
                        override fun onFailure(call: Call<Recette>, t: Throwable) {
                            Toast.makeText(this@AjouterRepasAdmin, t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }else{
                    val call = retrofitInterface!!.executeAddRepas(map)
                    call.enqueue(object : Callback<Recette> {
                        override fun onResponse(call: Call<Recette>, response: Response<Recette>) {
                            if (response.code() == 200) {
                                var editor: SharedPreferences.Editor = myshared!!.edit()
                                editor.putString(
                                    "idrecette",
                                    response.body()!!.recette._id.toString()
                                )
                                editor.putString("nomRecette", response.body()!!.recette.nomRecette)
                                editor.commit()
                                startActivity(i)
                            } else if (response.code() == 400) {
                                Toast.makeText(this@AjouterRepasAdmin, "400", Toast.LENGTH_LONG)
                                    .show()
                            } else if (response.code() == 401) {
                                Toast.makeText(this@AjouterRepasAdmin, "401", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<Recette>, t: Throwable) {
                            Toast.makeText(this@AjouterRepasAdmin, t.message, Toast.LENGTH_LONG)
                                .show()
                        }

                    })
                }



            }else{startActivity(i)
            }
        }




        saveRecette.setOnClickListener {
            val call = retrofitInterface!!.executeSaveRecette(myshared?.getString("idrecette",""))
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@AjouterRepasAdmin,response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    if (response.code()==200){
                        Toast.makeText(this@AjouterRepasAdmin,"recette saved",Toast.LENGTH_LONG).show()
                        var editor: SharedPreferences.Editor=myshared!!.edit()
                        editor.remove("idrecette")
                        editor.commit()
                        finish()
                    }else if (response.code()==400){
                        Toast.makeText(this@AjouterRepasAdmin,"400",Toast.LENGTH_LONG).show()
                    }else if (response.code()==401){
                        Toast.makeText(this@AjouterRepasAdmin,"401",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AjouterRepasAdmin, t.message, Toast.LENGTH_LONG).show()
                }

            })
        }


        cancelRecette.setOnClickListener {
            var editor: SharedPreferences.Editor=myshared!!.edit()
            editor.remove("idrecette")
            editor.commit()

            finish()
        }
    }

    fun AddRecette(map: java.util.HashMap<String?, String?>?,token:String){

    }
}