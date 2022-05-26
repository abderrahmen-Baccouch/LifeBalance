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

class Defit3Adapter(val c: Context, val defit3List:MutableList<Defit>,val mListener:onItemClickListener) : RecyclerView.Adapter<Defit3Adapter.Defit3ViewHolder> () {



    interface onItemClickListener{
        fun onItemClick(position: Int)
    }



    class Defit3ViewHolder(v: View,listener: onItemClickListener): RecyclerView.ViewHolder(v) {
        var nomDefitTV: TextView =v.findViewById<TextView>(R.id.nomDefit)
        var descTV: TextView =v.findViewById<TextView>(R.id.desc)
        var imageIV: ImageView =v.findViewById(R.id.imageDefit)
        init {
            v.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Defit3ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_defit3,parent,false)
        return Defit3ViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: Defit3ViewHolder, position: Int) {
        val newList = defit3List[position]
        holder.nomDefitTV.text = newList.nomDefit
        holder.descTV.text = newList.desc
        Glide.with(c).load(newList.imageURL).into(holder.imageIV)

    }

    override fun getItemCount(): Int {
        return defit3List.size
    }

}