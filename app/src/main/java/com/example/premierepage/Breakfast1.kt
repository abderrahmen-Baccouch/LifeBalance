package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.Ingredient
import com.example.premierepage.model.RecetteX
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.NotreRepasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Breakfast1 : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast1)
        var nomRepasTV: TextView =findViewById<TextView>(R.id.nomRepas)
        var caloriesTV: TextView =findViewById<TextView>(R.id.calories)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        var nomRepas=intent.getStringExtra("nomRepas")
        var calories=intent.getIntExtra("calories",0)
        var idRepas=intent.getStringExtra("idRepas")

        nomRepasTV.text=nomRepas
        caloriesTV.text=calories.toString()


        getIngredients(idRepas!!)



    }

    private fun getIngredients(id:String) {
        val recv: RecyclerView = findViewById(R.id.IngredientsRecycler)
        val call = retrofitInterface!!.executeAllIngredients(id)
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(context)
                        adapter= AlimentAdapter(context,response.body()!!,object: AlimentAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                // val i = Intent(context,FifthActivity::class.java)
                                // startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(this@Breakfast1, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }


}