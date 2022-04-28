package com.example.premierepage.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.R
import com.example.premierepage.model.Repa
import com.example.premierepage.model.Repas

class NotreRepasAdapter(val c: Context, val repasList: MutableList<Repa>,val mListener: onItemClickListener): RecyclerView.Adapter<NotreRepasAdapter.UserViewHolder> (){

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    inner class UserViewHolder(val v: View,listener:onItemClickListener) : RecyclerView.ViewHolder(v) {
        var nomRecetteTV: TextView
        var caloriesTV: TextView


        init {
            nomRecetteTV = v.findViewById<TextView>(R.id.nomRecette)
            caloriesTV = v.findViewById<TextView>(R.id.calories)
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotreRepasAdapter.UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_repas,parent,false)
        return UserViewHolder(v,mListener)
    }
    override fun onBindViewHolder(holder: NotreRepasAdapter.UserViewHolder, position: Int) {
        val newList = repasList[position]
        holder.nomRecetteTV.text = newList.nomRepas
        holder.caloriesTV.text = newList.calories.toString()
        /* holder.proteinesTV.text = newList.proteines
         holder.glucidesTV.text = newList.glucides
         holder.lipidesTV.text = newList.lipides*/

    }
    override fun getItemCount(): Int {
        return repasList.size
    }
}