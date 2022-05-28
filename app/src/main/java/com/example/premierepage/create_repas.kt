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
import com.example.premierepage.model.Recette
import com.example.premierepage.model.RecetteX
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.ExerciceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class create_repas : AppCompatActivity() {
    private lateinit var nomRecetteET: EditText
    private lateinit var addAlimentBtn:Button
    private lateinit var saveRecette:Button
    private lateinit var cancelRecette:Button
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_repas)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        /**bech njibou token mel fichier myshared */
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")

        nomRecetteET = findViewById(R.id.nomRecetteET)
        addAlimentBtn=findViewById(R.id.addAlimentBtn)
        saveRecette=findViewById(R.id.saveRecette)
        cancelRecette=findViewById(R.id.cancelRecette)
        val i=Intent(this@create_repas,ListeAliments::class.java)
        Toast.makeText(this@create_repas,nomRecetteET.toString(),Toast.LENGTH_LONG)
/*save.setonclicklisten
* if( nomRecetteET.isEmpty() wala mazel mafama 7ata aliment){
* nomRecetteET.error="nom de recette est obligatoire et au moins un aliment"
* else{
* e5dem 5edmet l back li trod verified true}
*
*/
    addAlimentBtn.setOnClickListener {
if (myshared?.getString("idrecette","")==""){
        /**5edmet l back*/
        val map = HashMap<String?, String?>()
        map["nomRecette"] = nomRecetteET.text.toString()

        val call = retrofitInterface!!.executeAddRecette(map,token)
        call.enqueue(object : Callback<Recette> {
            override fun onResponse(call: Call<Recette>, response: Response<Recette>) {
                if (response.code()==200){
                    Toast.makeText(this@create_repas,"recette created",Toast.LENGTH_LONG).show()
                    var editor: SharedPreferences.Editor=myshared!!.edit()
                    editor.putString("idrecette",response.body()!!.recette._id.toString())

                    editor.putString("nomRecette",response.body()!!.recette.nomRecette)
                    editor.commit()

                    startActivity(i)
                }else if (response.code()==400){
                    Toast.makeText(this@create_repas,"400",Toast.LENGTH_LONG).show()
                }else if (response.code()==401){
                    Toast.makeText(this@create_repas,"401",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Recette>, t: Throwable) {
                Toast.makeText(this@create_repas, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }else{startActivity(i)
    }
    }


        saveRecette.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["nomRecette"] = "a"
            map["temps"] = "a"
            val call = retrofitInterface!!.executeSaveRecette(myshared?.getString("idrecette",""),map)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@create_repas,response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    if (response.code()==200){
                        Toast.makeText(this@create_repas,"recette saved",Toast.LENGTH_LONG).show()
                        var editor: SharedPreferences.Editor=myshared!!.edit()
                        editor.remove("idrecette")
                        editor.commit()
                        finish()
                    }else if (response.code()==400){
                        Toast.makeText(this@create_repas,"400",Toast.LENGTH_LONG).show()
                    }else if (response.code()==401){
                        Toast.makeText(this@create_repas,"401",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@create_repas, t.message, Toast.LENGTH_LONG).show()
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





}