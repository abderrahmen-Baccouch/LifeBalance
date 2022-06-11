package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.premierepage.model.*
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.IngredientAdapter
import com.example.premierepage.view.NotreRepasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Breakfast1 : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    lateinit var addIngredient: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast1)
        /******************************Retrofit and intentGetExtra***************************/
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        val dateUser=myshared?.getString("dateUser","")
        var role =myshared?.getString("role","")
        var nomRepas=intent.getStringExtra("nomRepas")


        var calories=intent.getIntExtra("calories",0)
        var proteines=intent.getIntExtra("proteines",0)
        var glucides=intent.getIntExtra("glucides",0)
        var lipides=intent.getIntExtra("lipides",0)
        var tempsPreparation=intent.getIntExtra("temps",0)
        var idRepas=intent.getStringExtra("idRepas")
        var imageURL=intent.getStringExtra("imageURL")

        var idRecette=intent.getStringExtra("idRepas")

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

        var addBreakFast:androidx.cardview.widget.CardView=findViewById(R.id.addBreakFast)
        var addDejeuner:androidx.cardview.widget.CardView=findViewById(R.id.addDejeuner)
        var addDinner:androidx.cardview.widget.CardView=findViewById(R.id.addDinner)


        val map = HashMap<String?, String?>()
        map["createAt"] = dateUser

        addBreakFast.setOnClickListener {
            val call0 = retrofitInterface!!.executeCreateBreakfast(token,map)
            call0.enqueue(object : retrofit2.Callback<BreakfastX> {
                override fun onResponse(call: Call<BreakfastX>, response: Response<BreakfastX>) {
                    val call = retrofitInterface!!.executeAddBreakfast(token,idRecette,map)
                    call.enqueue(object : retrofit2.Callback<Void>{
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code()==200){
                                finish()
                            }else if (response.code()==400){
                                System.out.println("400")
                            }
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            System.out.println(t.message)
                        }
                    })
                }
                override fun onFailure(call: Call<BreakfastX>, t: Throwable) {
                    Toast.makeText(this@Breakfast1, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }



        addDejeuner.setOnClickListener {
            val call0 = retrofitInterface!!.executeCreateLunch(token,map)
            call0.enqueue(object : retrofit2.Callback<BreakfastX> {
                override fun onResponse(call: Call<BreakfastX>, response: Response<BreakfastX>) {
                    //if (response.code()==200){
                        val call = retrofitInterface!!.executeAddLunch(token,idRecette,map)
                        call.enqueue(object : retrofit2.Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.code()==200){
                                    finish()
                                }else if (response.code()==400){
                                    System.out.println("400")
                                }
                            }
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(this@Breakfast1, t.message, Toast.LENGTH_LONG).show()
                            }
                        })
                   /* }else if (response.code()==400){
                        Toast.makeText(this@Breakfast1,"400",Toast.LENGTH_LONG).show()
                    }*/
                }
                override fun onFailure(call: Call<BreakfastX>, t: Throwable) {
                    Toast.makeText(this@Breakfast1, t.message, Toast.LENGTH_LONG).show()
                }
            })



        }

        addDinner.setOnClickListener {
            val call0 = retrofitInterface!!.executeCreateDinner(token,map)
            call0.enqueue(object : retrofit2.Callback<BreakfastX> {
                override fun onResponse(call: Call<BreakfastX>, response: Response<BreakfastX>) {
                  System.out.println(response.code().toString())
                    if (response.code()==200){
                        val call = retrofitInterface!!.executeAddDinner(token,idRecette,map)
                        call.enqueue(object : retrofit2.Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                System.out.println(response.code().toString())
                                System.out.println(response.errorBody().toString())
                                if (response.code()==200){
                                    finish()
                                }else if (response.code()==400){
                                    Toast.makeText(this@Breakfast1,"400",Toast.LENGTH_LONG).show()
                                }
                            }
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(this@Breakfast1, t.message, Toast.LENGTH_LONG).show()
                                System.out.println(t.message)
                            }
                        })
                    }else if (response.code()==400){
                        Toast.makeText(this@Breakfast1,"400",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<BreakfastX>, t: Throwable) {
                    Toast.makeText(this@Breakfast1, t.message, Toast.LENGTH_LONG).show()
                }
            })



        }




        getIngredients(idRepas!!)
        addIngredient = findViewById(R.id.addIngredient)
        addIngredient.setOnClickListener {
            var editor: SharedPreferences.Editor = myshared!!.edit()
            editor.putString("idrecette",idRepas )
            editor.commit()
            val i= Intent(this@Breakfast1,ListeAliments::class.java)
    startActivity(i)

        }


    }
    override fun onResume() {
        super.onResume()
        var idRepas=intent.getStringExtra("idRepas")
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