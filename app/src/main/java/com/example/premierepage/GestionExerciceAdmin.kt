package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Exercices
import com.example.premierepage.view.ExerciceAdapter
import retrofit2.Call
import retrofit2.Response

class GestionExerciceAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    private lateinit var recv: RecyclerView
    lateinit var ajouter : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_exercice_admin)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.exercicesRecycler)
        getExercices()

        ajouter = findViewById(R.id.ajouter)
        ajouter.setOnClickListener {
            val i = Intent(this,AjouterExerciceAdmin::class.java)
            startActivity(i)
        }
    }/**fin OnCreate()*/


    fun getExercices(){
        val call = retrofitInterface!!.executeAllExercices()
        call.enqueue(object : retrofit2.Callback<MutableList<Exercices>> {
            override fun onResponse(call: Call<MutableList<Exercices>>, response: Response<MutableList<Exercices>>) {
                if (response.code()==200){
                    val listExercice=response.body()

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@GestionExerciceAdmin)
                        adapter= ExerciceAdapter(this@GestionExerciceAdmin,response.body()!!,object:
                            ExerciceAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {

                            }
                        }/*object:ExerciceAdapter.onItemClickListener{
                           override fun onItemClick(position: Int) {}}*/)
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<Exercices>>, t: Throwable) {
                Toast.makeText(this@GestionExerciceAdmin, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getExercices()
    }
}