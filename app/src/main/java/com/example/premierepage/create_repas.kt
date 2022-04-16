package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.premierepage.model.Aliments
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.ExerciceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class create_repas : AppCompatActivity() {
    private lateinit var nomRecetteET: EditText
    private lateinit var addAlimentBtn:Button
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_repas)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        /**bech njibou token mel fichier myshared */
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")

        nomRecetteET = findViewById(R.id.nomRecetteET)
        addAlimentBtn=findViewById(R.id.addAlimentBtn)
        val i=Intent(this@create_repas,AlimentsListe::class.java)
        Toast.makeText(this@create_repas,nomRecetteET.toString(),Toast.LENGTH_LONG)
/*save.setonclicklisten
* if( nomRecetteET.isEmpty() wala mazel mafama 7ata aliment){
* nomRecetteET.error="nom de recette est obligatoire et au moins un aliment"
* else{
* e5dem 5edmet l back li trod verified true}
*
*/
    addAlimentBtn.setOnClickListener {

        /**5edmet l back*/
        val map = HashMap<String?, String?>()
        map["nomRecette"] = nomRecetteET.text.toString()

        val call = retrofitInterface!!.executeAddRecette(map,token)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code()==200){
                    Toast.makeText(this@create_repas,"recette created",Toast.LENGTH_LONG).show()
                    startActivity(i)
                }else if (response.code()==400){
                    Toast.makeText(this@create_repas,"400",Toast.LENGTH_LONG).show()
                }else if (response.code()==401){
                    Toast.makeText(this@create_repas,"401",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@create_repas, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    }

}