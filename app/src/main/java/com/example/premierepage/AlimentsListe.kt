package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliment
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.Exercices
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.ExerciceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlimentsListe : AppCompatActivity() {
    private var listAliments: MutableList<Aliment>? =null
    private var retrofitInterface: RetrofitInterface? = null

    var myshared: SharedPreferences?=null
    private lateinit var recv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aliments_liste)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.listeAlimentRecycler)
        /**bech njibou token mel fichier myshared */
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")

        getAliments(token!!)

    }

    fun getAliments(t:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllAliments(t)
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@AlimentsListe)
                        adapter= AlimentAdapter(context,response.body()!!,object: ExerciceAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i = Intent(context,FifthActivity::class.java)
                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(this@AlimentsListe, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}