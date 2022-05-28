package com.example.premierepage.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.R
import com.example.premierepage.model.RecetteX
import com.example.premierepage.model.Repa
import com.example.premierepage.model.Repas

class NotreRepasAdapter(val c: Context, val repasList: MutableList<RecetteX>,val mListener: onItemClickListener): RecyclerView.Adapter<NotreRepasAdapter.UserViewHolder> (){

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    inner class UserViewHolder(val v: View,listener:onItemClickListener) : RecyclerView.ViewHolder(v) {
        var nomRecetteTV: TextView
        var caloriesTV: TextView
        var proteinesTV : TextView
        var glucidesTV : TextView
        var lipidesTV : TextView

        init {
            nomRecetteTV = v.findViewById<TextView>(R.id.nomRecette)
            caloriesTV = v.findViewById<TextView>(R.id.calories)
            proteinesTV = v.findViewById<TextView>(R.id.proteines)
            glucidesTV = v.findViewById<TextView>(R.id.glucides)
            lipidesTV = v.findViewById<TextView>(R.id.lipides)
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
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
        /* holder.proteinesTV.text = newList.proteines
         holder.glucidesTV.text = newList.glucides
         holder.lipidesTV.text = newList.lipides*/

    }
    override fun getItemCount(): Int {
        return repasList.size
    }
}