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

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListeAliments : AppCompatActivity() {

    private var retrofitInterface: RetrofitInterface? = null

    var myshared: SharedPreferences?=null
    private lateinit var recv: RecyclerView
    private lateinit var recv2: RecyclerView
    /**ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_aliments)
        /*********************** Retrofit and myshared *************************************************/
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        var role =myshared?.getString("role","")
        /*****************************************************************************************************/
        recv = findViewById(R.id.listeAlimentRecycler)
        recv2 = findViewById(R.id.listeAlimentRecycler2)
        if(role=="1"){
            getNotreAliments(role.toString())
        }else{
            getNotreAliments(role.toString())
            getAliments(token!!,role.toString())
        }
    }
    /**oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo    fin*/
    fun getAliments(t:String,role:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllAliments(t)
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){

                    recv2.apply {
                        recv2.layoutManager = LinearLayoutManager(this@ListeAliments)
                        adapter= AlimentAdapter(context,response.body()!!,role,object: AlimentAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                var editor: SharedPreferences.Editor=myshared!!.edit()
                                //   editor.putString("idrecette",response.body()!!.get(position).)
                                val i=Intent(this@ListeAliments,QuantiteAliment::class.java)
                                i.putExtra("idaliment",response.body()!!.get(position)._id)
                                startActivity(i)
                                finish()
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(this@ListeAliments, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    fun getNotreAliments(role: String){
        val call = retrofitInterface!!.executeAllNotreAliments()
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(context)
                        adapter= AlimentAdapter(context,response.body()!!,role,object: AlimentAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                var editor: SharedPreferences.Editor=myshared!!.edit()
                                //   editor.putString("idrecette",response.body()!!.get(position).)
                                val i=Intent(this@ListeAliments,QuantiteAliment::class.java)
                                i.putExtra("idaliment",response.body()!!.get(position)._id)
                                startActivity(i)
                                finish()
                            }
                        })
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(this@ListeAliments, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}