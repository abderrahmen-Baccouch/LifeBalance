package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.premierepage.models.AlimentModel
import retrofit2.Call

import kotlin.collections.ArrayList
import retrofit2.Callback
import retrofit2.Response

class Alimentsss : AppCompatActivity() {

    private var retrofitInterface: RetrofitInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alimentsss)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        //getAllAliments()

    }

  /*  private fun getAllAliments() {
        val call = retrofitInterface!!.getAliments()
        call.enqueue(object :Callback<ArrayList<AlimentModel>>{
            override fun onResponse(
                call: Call<ArrayList<AlimentModel>>,
                response: Response<ArrayList<AlimentModel>>
            ) {
                if (response.code() == 200) {
                    val result = response.body()


                    Toast.makeText(
                        this@Alimentsss, result?.get(1)?.getnomAliment(),
                        Toast.LENGTH_LONG
                    ).show()




                } else if (response.code() == 400) {
                    val result = response.body()

                    Toast.makeText(
                        this@Alimentsss, "Wrong Credentials",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<AlimentModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        }

        )
    }*/
}