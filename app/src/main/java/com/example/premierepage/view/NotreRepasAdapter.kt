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
import com.example.premierepage.model.RecetteX
import com.example.premierepage.model.Repa
import com.example.premierepage.model.Repas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotreRepasAdapter(val c: Context, val repasList: MutableList<RecetteX>,val mListener: onItemClickListener): RecyclerView.Adapter<NotreRepasAdapter.UserViewHolder> (){
    private var retrofitInterface: RetrofitInterface? = null
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    inner class UserViewHolder(val v: View,listener:onItemClickListener) : RecyclerView.ViewHolder(v) {
        var nomRecetteTV: TextView
        var caloriesTV: TextView
        var proteinesTV : TextView
        var glucidesTV : TextView
        var lipidesTV : TextView
        var imageIV:ImageView
        var mMenus : ImageView


        init {
            nomRecetteTV = v.findViewById<TextView>(R.id.nomRecette)
            caloriesTV = v.findViewById<TextView>(R.id.calories)
            proteinesTV = v.findViewById<TextView>(R.id.proteines)
            glucidesTV = v.findViewById<TextView>(R.id.glucides)
            imageIV=v.findViewById<ImageView>(R.id.imageRecette)
            lipidesTV = v.findViewById<TextView>(R.id.lipides)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        @SuppressLint("DiscouragedPrivateApi", "NotifyDataSetChanged")
        private fun popupMenus(v:View) {
            val retrofit = RetrofitClient.getInstance()
            retrofitInterface = retrofit.create(RetrofitInterface::class.java)

            val position = repasList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText ->{
                        val v = LayoutInflater.from(c).inflate(R.layout.activity_ajouter_repas_admin,null)
                        val nomRepasET = v.findViewById<EditText>(R.id.nomRepas)
                        val tempsPreparationET = v.findViewById<EditText>(R.id.tempsPreparation)

                        nomRepasET.setText(position.nomRecette)
                     //   tempsPreparationET.setText(position.temps)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Update"){
                                    dialog,_->
                                nomRecetteTV.setText(nomRepasET.text.toString())


                                val map = HashMap<String?, String?>()
                                map["nomRecette"] = nomRepasET.text.toString()
                                map["temps"] = tempsPreparationET.text.toString()

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
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are You Sure To Delete This Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                val call = retrofitInterface!!.executeDeleteRecette(position._id)
                                call!!.enqueue(object : Callback<Void?> {
                                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                        if (response.code()==200){
                                            Toast.makeText(c,"Successfuly deleted recette", Toast.LENGTH_LONG).show()
                                        }else if (response.code()==400){
                                            Toast.makeText(c,"errrror", Toast.LENGTH_LONG)
                                        }
                                    }
                                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                                        Toast.makeText(c, t.message, Toast.LENGTH_LONG).show()
                                    }
                                })
                               repasList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted This Information", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
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


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotreRepasAdapter.UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_recette,parent,false)
        return UserViewHolder(v,mListener)
    }
    override fun onBindViewHolder(holder: NotreRepasAdapter.UserViewHolder, position: Int) {
        val newList = repasList[position]
        holder.nomRecetteTV.text = newList.nomRecette
        holder.caloriesTV.text = newList.calories.toString()+"cal"
        holder.proteinesTV.text = newList.proteines.toString()+" g"
        holder.glucidesTV.text = newList.glucides.toString()+" g"
        holder.lipidesTV.text = newList.lipides.toString()+" g"
        Glide.with(c).load(newList.imageURL).into(holder.imageIV)
        /* holder.proteinesTV.text = newList.proteines
         holder.glucidesTV.text = newList.glucides
         holder.lipidesTV.text = newList.lipides*/

    }
    override fun getItemCount(): Int {
        return repasList.size
    }
}