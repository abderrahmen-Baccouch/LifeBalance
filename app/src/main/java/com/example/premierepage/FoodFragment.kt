package com.example.premierepage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.BreakfastX
import com.example.premierepage.model.RecetteX
import com.example.premierepage.view.NotreRepasAdapter
import com.example.premierepage.view.RecetteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoodFragment : Fragment() {

    private lateinit var Btn: FloatingActionButton
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null

    private lateinit var recv: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recv = view!!.findViewById(R.id.recettesRecycler)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        /**bech njibou token mel fichier myshared */
        myshared=context?.getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")

        getRecettes(token!!)

        Btn = view!!.findViewById(R.id.addBtn)
        Btn.setOnClickListener {
            val intent = Intent(context,create_repas::class.java)
            startActivity(intent)
        }
    }
    fun getRecettes(t:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllRecettes(t)
        call.enqueue(object : Callback<MutableList<RecetteX>> {
            override fun onResponse(call: Call<MutableList<RecetteX>>, response: Response<MutableList<RecetteX>>) {
                var listRepas=response.body()!!
                if (response.code()==200){
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(activity)
                        adapter= RecetteAdapter(context,response.body()!!,object: RecetteAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i = Intent(context,Repas::class.java)
                                Toast.makeText(context,"aa",Toast.LENGTH_LONG).show()
                                i.putExtra("nomRecette",listRepas.get(position).nomRecette)
                                i.putExtra("idRecette",listRepas.get(position)._id)
                               // i.putExtra("calories",listRepas.get(position).calories)

                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<RecetteX>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

   /*fun getBreakfast(t:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllBreakfast(t)
        call.enqueue(object : Callback<MutableList<BreakfastX>> {
            override fun onResponse(call: Call<MutableList<BreakfastX>>, response: Response<MutableList<BreakfastX>>) {
                var listRepas=response.body()!!
                if (response.code()==200){
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(activity)
                        adapter= RecetteAdapter(context,response.body()!!.get(0).recettes,object: RecetteAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i = Intent(context,Repas::class.java)
                                Toast.makeText(context,"aa",Toast.LENGTH_LONG).show()
                              //  i.putExtra("nomRecette",listRepas.get(position).nomRecette)
                                i.putExtra("idRecette",listRepas.get(position)._id)
                                // i.putExtra("calories",listRepas.get(position).calories)

                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<BreakfastX>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }*/

    //wala onRestar w t7ot feha getRecettes
    override fun onResume() {
        super.onResume()
        /**bech njibou token mel fichier myshared */
        myshared=context?.getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        getRecettes(token!!)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_food,container,false)
    }
}