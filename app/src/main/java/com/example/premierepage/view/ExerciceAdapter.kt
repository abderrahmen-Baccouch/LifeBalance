package com.example.premierepage.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.premierepage.R
import com.example.premierepage.RetrofitClient
import com.example.premierepage.RetrofitInterface
import com.example.premierepage.model.Exercices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciceAdapter(val c: Context, val exercicesList:MutableList<Exercices>,val role:String,val mListener:onItemClickListener): RecyclerView.Adapter<ExerciceAdapter.ExerciceViewHolder> () {
    private var retrofitInterface: RetrofitInterface? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    inner class ExerciceViewHolder(v: View,listener:onItemClickListener): RecyclerView.ViewHolder(v) {
        var nomExerciceTV: TextView=v.findViewById<TextView>(R.id.nomExercice)
        var caloriesTV: TextView=v.findViewById<TextView>(R.id.calories)
        var durationTV: TextView=v.findViewById<TextView>(R.id.duration)
        var imageIV:ImageView=v.findViewById<ImageView>(R.id.imageExercice)
        var mMenus : ImageView
        init { mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
            if(role=="0"){
                mMenus.visibility= View.GONE
            }
        }




        @SuppressLint("DiscouragedPrivateApi", "NotifyDataSetChanged")
        private fun popupMenus(v:View) {
            val retrofit = RetrofitClient.getInstance()
            retrofitInterface = retrofit.create(RetrofitInterface::class.java)

            val position =exercicesList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText ->{
                        val v = LayoutInflater.from(c).inflate(R.layout.activity_ajouter_exercice_admin,null)
                        val nomExerciceET = v.findViewById<EditText>(R.id.nomExercice)
                        val caloriesET = v.findViewById<EditText>(R.id.calories)

                        nomExerciceET.setText(position.nomExercice)
                        caloriesET.setText(position.calories)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Modifier"){
                                    dialog,_->
                                position.nomExercice = nomExerciceET.text.toString()
                                position.calories = caloriesET.text.toString()+" cal"
                                val map = HashMap<String?, String?>()
                                map["nomExercice"] = nomExerciceET.text.toString()
                                map["calories"] = caloriesET.text.toString()

                                val call = retrofitInterface!!.executeUpdateExercice(map,position._id)
                                call!!.enqueue(object : Callback<Void?> {
                                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                        if (response.code()==200){
                                            Toast.makeText(c, "success", Toast.LENGTH_LONG).show()
                                            notifyDataSetChanged()
                                        }else if (response.code()==400){
                                            Toast.makeText(c, "error1", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                                        Toast.makeText(c, t.message, Toast.LENGTH_LONG).show()
                                    }

                                })
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.delete ->{
                        AlertDialog.Builder(c)
                            .setTitle("Supprimer")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Étes-vous certain de vouloir supprimer cette information")
                            .setPositiveButton("Oui"){
                                    dialog,_->
                                if(role=="1") {
                                    val call = retrofitInterface!!.executeDeleteExercice(position._id)
                                    call!!.enqueue(object : Callback<Void?> {
                                        override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                            if (response.code() == 200) {
                                                System.out.println("Exercice  deleted 200")
                                            } else if (response.code() == 400) {
                                                System.out.println("400 123")
                                            }
                                        }
                                        override fun onFailure(call: Call<Void?>, t: Throwable) {
                                            System.out.println("failure:"+t.message)
                                        }

                                    })
                                }else{
                                    val call = retrofitInterface!!.executeDeleteExerciceTermine(position._id)
                                    call!!.enqueue(object : Callback<Void?> {
                                        override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                            if (response.code() == 200) {
                                                System.out.println("Exercice termine deleted 200")

                                            } else if (response.code() == 400) {
                                                System.out.println("400")
                                                System.out.println(response.errorBody())
                                            }
                                        }
                                        override fun onFailure(call: Call<Void?>, t: Throwable) {
                                            System.out.println("failure")
                                        }

                                    })
                                }
                                exercicesList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted This Information", Toast.LENGTH_SHORT).show()

                                dialog.dismiss()
                            }
                            .setNegativeButton("Non"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)

        }

        /********************************************************************************/
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_exercices_admin,parent,false)
        return ExerciceViewHolder(v,mListener)
    }



    override fun onBindViewHolder(holder: ExerciceViewHolder, position: Int) {
        val newList = exercicesList[position]
        holder.nomExerciceTV.text = newList.nomExercice
        holder.caloriesTV.text = newList.calories+"cal"
        holder.durationTV.text=newList.duration.toString()+"min"

        Glide.with(c).load(newList.imageURL).into(holder.imageIV)
        //Glide.with(c).load("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300").into(holder.imageIV)

    }

    override fun getItemCount(): Int {
        return exercicesList.size
    }



}