package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.RecetteX
import com.example.premierepage.view.NotreRepasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DinnerAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    lateinit var ajouter : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner_admin)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        val map = HashMap<String?, String?>()
        map["typeRepas"] ="3"
        getRepas(map)
        ajouter= findViewById(R.id.ajouter_diner)
        ajouter.setOnClickListener {
            val i = Intent(this, AjouterRepasAdmin::class.java)
            startActivity(i)
        }
    }

    fun getRepas(map: java.util.HashMap<String?, String?>?){
        //5edmet l affichage mte3 les aliments
        val recv: RecyclerView = findViewById(R.id.RepasRecycler)
        val call = retrofitInterface!!.executeAllRepas(map)
        call.enqueue(object : Callback<MutableList<RecetteX>> {
            override fun onResponse(call: Call<MutableList<RecetteX>>, response: Response<MutableList<RecetteX>>) {
                if (response.code()==200){

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(context)
                        adapter= NotreRepasAdapter(context,response.body()!!,object: NotreRepasAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                // val i = Intent(context,FifthActivity::class.java)
                                // startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<RecetteX>>, t: Throwable) {
                Toast.makeText(this@DinnerAdmin, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}