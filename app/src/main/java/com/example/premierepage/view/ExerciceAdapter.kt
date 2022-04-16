package com.example.premierepage.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.premierepage.R
import com.example.premierepage.RetrofitInterface
import com.example.premierepage.model.Exercices

class ExerciceAdapter(val c: Context, val exercicesList:MutableList<Exercices>,val mListener:onItemClickListener): RecyclerView.Adapter<ExerciceAdapter.ExerciceViewHolder> () {



    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

   /*fun setOnItemClickListener(listener: onItemClickListener){
      mListener=listener
    }*/



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_exercice,parent,false)
        return ExerciceViewHolder(v,mListener)
    }



    override fun onBindViewHolder(holder: ExerciceViewHolder, position: Int) {
        val newList = exercicesList[position]
        holder.nomExerciceTV.text = newList.nomExercice
        holder.caloriesTV.text = newList.calories
        Glide.with(c).load(newList.imageURL).into(holder.imageIV)
        //Glide.with(c).load("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300").into(holder.imageIV)

    }

    override fun getItemCount(): Int {
        return exercicesList.size
    }

    class ExerciceViewHolder(v: View,listener:onItemClickListener): RecyclerView.ViewHolder(v) {
        var nomExerciceTV: TextView=v.findViewById<TextView>(R.id.nomExercice)
        var caloriesTV: TextView=v.findViewById<TextView>(R.id.calories)
        var imageIV:ImageView=v.findViewById<ImageView>(R.id.imageExercice)

        init {
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }


}