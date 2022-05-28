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
import com.example.premierepage.view.IngredientAdapter
import com.example.premierepage.view.NotreRepasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Breakfast1 : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast1)
        /******************************Retrofit and intentGetExtra***************************/
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        var nomRepas=intent.getStringExtra("nomRepas")

        var calories=intent.getIntExtra("calories",0)
        var proteines=intent.getIntExtra("proteines",0)
        var glucides=intent.getIntExtra("glucides",0)
        var lipides=intent.getIntExtra("lipides",0)
        var tempsPreparation=intent.getIntExtra("temps",0)
        var idRepas=intent.getStringExtra("idRepas")


        /******************************findViewById********************************************/
        var nomRepasTV: TextView =findViewById<TextView>(R.id.nomRepas)
        var caloriesTV: TextView =findViewById<TextView>(R.id.calories)
        var proteinesTV: TextView =findViewById<TextView>(R.id.proteines)
        var glucidesTV: TextView =findViewById<TextView>(R.id.glucides)
        var lipidesTV: TextView =findViewById<TextView>(R.id.lipides)
        var tempsPreparationTV: TextView =findViewById<TextView>(R.id.tempsPreparation)



        nomRepasTV.text=nomRepas
        caloriesTV.text=calories.toString()+"cal"
        lipidesTV.text=lipides.toString()+"g"
        proteinesTV.text=proteines.toString()+"g"
        glucidesTV.text=glucides.toString()+"g"
        tempsPreparationTV.text=tempsPreparation.toString()+"min"


        getIngredients(idRepas!!)

    }

    private fun getIngredients(idRepas:String) {
        val recv: RecyclerView = findViewById(R.id.IngredientsRecycler)
        val call = retrofitInterface!!.executeAllIngredients(idRepas)
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(context)
                        adapter= IngredientAdapter(context,response.body()!!,idRepas,object: IngredientAdapter.onItemClickListener{
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