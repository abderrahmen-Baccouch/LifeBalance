package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Defit
import com.example.premierepage.view.Defit3Adapter
import com.example.premierepage.view.DefitAdapter
import kotlinx.android.synthetic.main.activity_defit3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Defit3 : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null

    var myshared: SharedPreferences?=null
    private lateinit var recv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defit3)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.listeDefit3Recycler)

        getDefits3()

    }

    fun getDefits3(){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllDefit3()
        call.enqueue(object : Callback<MutableList<Defit>> {
            override fun onResponse(call: Call<MutableList<Defit>>, response: Response<MutableList<Defit>>) {
                if (response.code()==200){
                    val listDefit3=response.body()

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@Defit3)
                        adapter= Defit3Adapter(this@Defit3,response.body()!!,object: Defit3Adapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i =Intent(this@Defit3,burpees::class.java)
                                //  Toast.makeText(this@ListeDefit, listDefit?.get(position)?._id,Toast.LENGTH_LONG).show()
                                i.putExtra("iddefit",listDefit3?.get(position)?._id)
                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<Defit>>, t: Throwable) {
                Toast.makeText(this@Defit3, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}