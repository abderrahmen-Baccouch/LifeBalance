package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.DefitAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.premierepage.model.Defit
import com.example.premierepage.view.ExerciceAdapter

class ListeDefit : AppCompatActivity() {

    private var retrofitInterface: RetrofitInterface? = null

    var myshared: SharedPreferences?=null
    private lateinit var recv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_defit)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.listeDefitRecycler)

        getDefits()



    }
    fun getDefits(){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllDefit()
        call.enqueue(object : Callback<MutableList<Defit>> {
            override fun onResponse(call: Call<MutableList<Defit>>, response: Response<MutableList<Defit>>) {
                if (response.code()==200){
            val listDefit=response.body()

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@ListeDefit)
                        adapter= DefitAdapter(this@ListeDefit,response.body()!!,object: DefitAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i =Intent(this@ListeDefit,bow::class.java)
                              //  Toast.makeText(this@ListeDefit, listDefit?.get(position)?._id,Toast.LENGTH_LONG).show()
                                i.putExtra("iddefit",listDefit?.get(position)?._id)
                                i.putExtra("imageURL",listDefit?.get(position)?.imageURL)
                                /*i.putExtra("id",listExercice?.get(position)?._id)*/
                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<Defit>>, t: Throwable) {
                Toast.makeText(this@ListeDefit, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}