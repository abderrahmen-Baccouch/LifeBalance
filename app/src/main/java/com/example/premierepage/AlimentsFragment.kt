package com.example.premierepage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.ExerciceAdapter

import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AlimentsFragment : Fragment(R.layout.fragment_aliments){
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null

    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet", "NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**set find Id*/
        addsBtn = view!!.findViewById(R.id.addingBtn)
        recv = view!!.findViewById(R.id.alimentsRecycler)

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        /**bech njibou token mel fichier myshared */
        myshared=context?.getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")




        getAliments(token!!)

        addsBtn.setOnClickListener {
            val inflter = LayoutInflater.from(this.context)
            val v = inflter.inflate(R.layout.add_item,null)

            /** v hya add_item */
            val nomAlimentET = v.findViewById<EditText>(R.id.nomAliment)
            val caloriesET = v.findViewById<EditText>(R.id.calories)
            val proteinesET = v.findViewById<EditText>(R.id.proteines)
            val glucidesET = v.findViewById<EditText>(R.id.glucides)
            val lipidesET = v.findViewById<EditText>(R.id.lipides)

            val addDialog = AlertDialog.Builder(this.context)

            addDialog.setView(v)
            addDialog.setPositiveButton("ADD"){
                    dialog,_->

                val nomAliment =nomAlimentET.text.toString()
                val calories = caloriesET.text.toString()
                val proteines = proteinesET.text.toString()
                val glucides = glucidesET.text.toString()
                val lipides = lipidesET.text.toString()

                   val map = HashMap<String?, String?>()
                   map["nomAliment"] = nomAliment
                   map["calories"] = calories
                   map["proteines"] = proteines
                   map["glucides"] = glucides
                   map["lipides"] = lipides

                   val call = retrofitInterface!!.executeAddAliment(map, token)
                   call!!.enqueue(object : Callback<Void?> {
                       override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                           if (response.code() == 200) {
                               Toast.makeText(context, "aliment ajouté", Toast.LENGTH_LONG).show()
                               /**to93ed fama mochkla : ki tzid aliment yetzed fil base
                                * ama mayetzedech fil front lazem to5rej mel activty w t3awed tod5ol bech yetra tzed*/

                               getAliments(token!!)
                               dialog.dismiss()
                           } else if (response.code() == 401) {
                               Toast.makeText(context, "aliment existe", Toast.LENGTH_LONG).show()
                           } else if (response.code() == 400) {
                               Toast.makeText(context, "an error occured while saving aliment", Toast.LENGTH_LONG).show()
                           }
                       }

                       override fun onFailure(call: Call<Void?>?, t: Throwable) {
                           Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                       }
                   })

            }

            addDialog.setNegativeButton("CANCEL"){
                    dialog,_->
                    dialog.dismiss()
            }
            addDialog.create()
            addDialog.show()
        }

    }

    fun getAliments(t:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllAliments(t)
        call.enqueue(object :Callback<MutableList<Aliments>>{
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){

                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(activity)
                        adapter=AlimentAdapter(context,response.body()!!,object: AlimentAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i = Intent(context,FifthActivity::class.java)
                               // startActivity(i)
                            }
                        })
                    }
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_aliments,container,false)
    }
}