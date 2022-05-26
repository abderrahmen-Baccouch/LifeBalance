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
import com.example.premierepage.model.Defit
import com.example.premierepage.model.Exercices
import pl.droidsonroids.gif.GifImageView

class DefitAdapter(val c: Context, val defitList:MutableList<Defit>,val mListener:onItemClickListener) : RecyclerView.Adapter<DefitAdapter.DefitViewHolder> () {



    interface onItemClickListener{
        fun onItemClick(position: Int)
    }



    class DefitViewHolder(v: View,listener: onItemClickListener): RecyclerView.ViewHolder(v) {
        var nomDefitTV: TextView =v.findViewById<TextView>(R.id.nomDefit)
        var repetitionTV: TextView =v.findViewById<TextView>(R.id.repetition)
        var dureeTV: TextView =v.findViewById<TextView>(R.id.duree)
        var imageIV: ImageView =v.findViewById(R.id.imageDefit)
        init {
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_defit,parent,false)
        return DefitViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: DefitViewHolder, position: Int) {
        val newList = defitList[position]
        holder.nomDefitTV.text = newList.nomDefit
        holder.repetitionTV.text = "Repéter "+newList.repetition.toString()+" fois"
        holder.dureeTV.text = newList.duree.toString()+"min"
        Glide.with(c).load(newList.imageURL).into(holder.imageIV)

    }

    override fun getItemCount(): Int {
        return defitList.size
    }

}