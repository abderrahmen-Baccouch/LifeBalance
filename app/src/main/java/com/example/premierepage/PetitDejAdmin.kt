package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.RecetteX
import com.example.premierepage.model.Repa
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.NotreRepasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetitDejAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    lateinit var ajouter : Button
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petit_dej_admin)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        myshared=getSharedPreferences("myshared",0)
        var role=myshared?.getString("role","")

        val map = HashMap<String?, String?>()
        map["typeRepas"] ="1"
        getRepas(map)

        ajouter= findViewById(R.id.ajouter_petitDej)
        val ajouterCard: CardView = findViewById(R.id.ajouterCard)

        if(role!="1"){
            ajouterCard.visibility= View.GONE
        }

        ajouter.setOnClickListener {
            val i = Intent(this,AjouterRepasAdmin::class.java)
            i.putExtra("typeRepas","1")
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
                    var listRepas=response.body()!!
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(context)
                        adapter= NotreRepasAdapter(context,response.body()!!,object: NotreRepasAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i = Intent(context,Breakfast1::class.java)
                                i.putExtra("nomRepas",listRepas.get(position).nomRecette)
                                i.putExtra("idRepas",listRepas.get(position)._id)
                                i.putExtra("calories",listRepas.get(position).calories)
                                i.putExtra("proteines",listRepas.get(position).proteines)
                                i.putExtra("glucides",listRepas.get(position).glucides)
                                i.putExtra("lipides",listRepas.get(position).lipides)
                                i.putExtra("temps",listRepas.get(position).temps)


                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<RecetteX>>, t: Throwable) {
                Toast.makeText(this@PetitDejAdmin, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    override fun onResume() {
        super.onResume()
        val map = HashMap<String?, String?>()
        map["typeRepas"] ="1"
        getRepas(map)
    }
}