package com.example.premierepage.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.R
import com.example.premierepage.RetrofitClient
import com.example.premierepage.RetrofitInterface
import com.example.premierepage.model.RecetteX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecetteAdapter(val c: Context, val recettesList:MutableList<RecetteX>,val mListener:onItemClickListener) : RecyclerView.Adapter<RecetteAdapter.UserViewHolder> () {
    private var retrofitInterface: RetrofitInterface? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }


    inner  class UserViewHolder(val v: View,listener:onItemClickListener):RecyclerView.ViewHolder(v){
        var nomRecetteTV: TextView
        var caloriesTV : TextView

        // var mMenus : ImageView
        init {
            nomRecetteTV = v.findViewById<TextView>(R.id.nomRecette)
            caloriesTV = v.findViewById<TextView>(R.id.calories)
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }

        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_recette,parent,false)
        return UserViewHolder(v,mListener)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = recettesList[position]
        holder.nomRecetteTV.text = newList.nomRecette
        holder.caloriesTV.text = newList.calories.toString()
        /* holder.proteinesTV.text = newList.proteines
         holder.glucidesTV.text = newList.glucides
         holder.lipidesTV.text = newList.lipides*/

    }
    override fun getItemCount(): Int {
        return recettesList.size
    }


}

