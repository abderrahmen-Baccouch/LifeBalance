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


        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.exercicesRecycler)

        val call = retrofitInterface!!.executeAllExercices()
        call.enqueue(object : retrofit2.Callback<MutableList<Exercices>> {
            override fun onResponse(call: Call<MutableList<Exercices>>, response: Response<MutableList<Exercices>>) {
                if (response.code()==200){
                    listExercice=response.body()

                   recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@ActivityExercices)
                       adapter= ExerciceAdapter(this@ActivityExercices,response.body()!!,object:ExerciceAdapter.onItemClickListener{
                           override fun onItemClick(position: Int) {
                               val i =Intent(this@ActivityExercices,Yoga::class.java)
                               Toast.makeText(this@ActivityExercices,
                                   listExercice?.get(position)?._id,Toast.LENGTH_LONG).show()
                              i.putExtra("nomExercice",listExercice?.get(position)?.nomExercice)
                             i.putExtra("calories",listExercice?.get(position)?.calories)
                               i.putExtra("id",listExercice?.get(position)?._id)
                              startActivity(i)
                           }
                       }/*object:ExerciceAdapter.onItemClickListener{
                           override fun onItemClick(position: Int) {}}*/)
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<Exercices>>, t: Throwable) {
                Toast.makeText(this@ActivityExercices, t.message, Toast.LENGTH_LONG).show()
            }
        })


/*exercicesRecycler.setOnClickListener{
    val intent =Intent(this,Yoga::class.java)
  //  var nomE=exerciceID.nomExercice.toString()
 //   Toast.makeText(this,nomE,Toast.LENGTH_LONG).show()
   // intent.putExtra("nomExercice",nomE)
   startActivity(intent)
}*/


//var adapter=ExerciceAdapter(this@ActivityExercices,listExercice!!)
       /* adapter.setOnItemClickListener(object:ExerciceAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@ActivityExercices,"You Clicked on item no$position ",Toast.LENGTH_LONG).show()
            }

        })*/




        myCustomActivity.setOnClickListener {
            val intent =Intent(this,customActivity::class.java)
            startActivity(intent)
        }



    }
}