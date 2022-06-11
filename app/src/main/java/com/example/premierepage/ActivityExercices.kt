package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.Exercices
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.ExerciceAdapter
import kotlinx.android.synthetic.main.activity_exercices.*
import kotlinx.android.synthetic.main.item_exercice.*
import kotlinx.android.synthetic.main.item_exercice.view.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ActivityExercices : AppCompatActivity() {
    private var listExercice: MutableList<Exercices>? =null
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    private lateinit var recv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercices)
        /**------------------------------------------Retrofit et myshared --------------------------*/
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var role=myshared?.getString("role","")

        /**------------------------------------------findViewById--------------------------*/
        recv = findViewById(R.id.exercicesRecycler)

        getExercices(role.toString())

        myCustomActivity.setOnClickListener {
            val intent =Intent(this,workoutGoal::class.java)
            startActivity(intent)
        }
    }
    /**-------------------------------------------fin onCreate----------------------------------------*/

    fun getExercices(role:String){
        val call = retrofitInterface!!.executeAllExercices()
        call.enqueue(object : retrofit2.Callback<MutableList<Exercices>> {
            override fun onResponse(call: Call<MutableList<Exercices>>, response: Response<MutableList<Exercices>>) {
                if (response.code()==200){
                    listExercice=response.body()
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@ActivityExercices)
                        adapter= ExerciceAdapter(this@ActivityExercices,response.body()!!,"0",object:ExerciceAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i =Intent(this@ActivityExercices,Yoga::class.java)
                                i.putExtra("nomExercice",listExercice?.get(position)?.nomExercice)
                                i.putExtra("calories",listExercice?.get(position)?.calories)
                                i.putExtra("imageURL",listExercice?.get(position)?.imageURL)
                                i.putExtra("id",listExercice?.get(position)?._id)
                                startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<Exercices>>, t: Throwable) {
                Toast.makeText(this@ActivityExercices, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        myshared=getSharedPreferences("myshared",0)
        var role=myshared?.getString("role","")
        getExercices(role.toString())
    }
}