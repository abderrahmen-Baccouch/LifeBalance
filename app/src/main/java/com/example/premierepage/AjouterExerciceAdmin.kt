package com.example.premierepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

import okhttp3.MediaType.Companion.toMediaTypeOrNull


import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AjouterExerciceAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_exercice_admin)
        /**-------------------------------------retrofit-------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)


        val nomExerciceET = findViewById<EditText>(R.id.nomExercice)
        val caloriesET = findViewById<EditText>(R.id.calories)
        val save_exercice= findViewById<Button>(R.id.save_exercice)


        save_exercice.setOnClickListener {

            val nomExercice = nomExerciceET.text.toString()
            val calories = caloriesET.text.toString()
            val map = HashMap<String?, String?>()
            map["nomExercice"] = nomExercice
            map["calories"] = calories

            val filesDir = applicationContext.filesDir
            val file = File(filesDir, "image" + ".png")
            val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body :  MultipartBody.Part = createFormData("myFile", file.name, reqFile)
            val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "myFile")




            val call = retrofitInterface!!.executeAddExercice(map/*,body,name*/)
            call!!.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        Toast.makeText(this@AjouterExerciceAdmin, "aliment ajouté", Toast.LENGTH_LONG).show()
                        finish()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@AjouterExerciceAdmin, "aliment existe", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        Toast.makeText(this@AjouterExerciceAdmin, "an error occured while saving aliment", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void?>?, t: Throwable) {
                    Toast.makeText(this@AjouterExerciceAdmin, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }




    }
}