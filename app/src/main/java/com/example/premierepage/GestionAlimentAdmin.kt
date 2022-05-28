package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.view.AlimentAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionAlimentAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    lateinit var ajouter : Button
    private lateinit var recv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_aliment_admin)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var role=myshared?.getString("role","")
        recv = findViewById(R.id.alimentsRecycler)
        getNotreAliments(role.toString())

        ajouter = findViewById(R.id.ajouter)
        ajouter.setOnClickListener {
            val i = Intent(this,AjouterAlimentAdmin::class.java)
            startActivity(i)
        }
    }

    fun getNotreAliments(role:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllNotreAliments()
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(context)
                        adapter= AlimentAdapter(context,response.body()!!,role,object: AlimentAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                            }
                        })
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(this@GestionAlimentAdmin, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    override fun onResume() {
        super.onResume()
        myshared=getSharedPreferences("myshared",0)
        var role=myshared?.getString("role","")
        getNotreAliments(role.toString())
    }
}