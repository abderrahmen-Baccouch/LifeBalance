package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.Eau
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.EauAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsommationEau : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consommation_eau)
        /**-------------------------------------retrofit and myshared-------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        val addEauBtn: ImageView =findViewById(R.id.addEauBtn)
        val quantiteEau:EditText=findViewById(R.id.quantite)


        addEauBtn.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["quantite"] = quantiteEau.text.toString()
            val call = retrofitInterface!!.executeAddEau(token,map)
            call!!.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        finish()
                    } else if (response.code() == 400) {
                        Toast.makeText(this@ConsommationEau, "an error occured while saving Eau", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void?>?, t: Throwable) {
                    Toast.makeText(this@ConsommationEau, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        getEaux(token!!)
    }

    fun getEaux(t:String){
        val recv:RecyclerView=findViewById(R.id.eauRecycler)
        val call = retrofitInterface!!.executeAllEau(t)

        call.enqueue(object : Callback<MutableList<Eau>> {
            override fun onResponse(call: Call<MutableList<Eau>>, response: Response<MutableList<Eau>>) {
                if (response.code()==200){

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@ConsommationEau)
                        adapter= EauAdapter(context,response.body()!!)
                    }
                }else if (response.code()==400){
                    Toast.makeText(this@ConsommationEau,"400",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Eau>>, t: Throwable) {
                Toast.makeText(this@ConsommationEau, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}