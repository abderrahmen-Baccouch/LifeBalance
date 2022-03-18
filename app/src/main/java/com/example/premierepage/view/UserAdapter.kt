package com.example.premierepage.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.R
import com.example.premierepage.model.UserData

class UserAdapter (val c:Context,val userList:ArrayList<UserData>) : RecyclerView.Adapter<UserAdapter.UserViewHolder> () {

    inner  class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        val nameFo = v.findViewById<TextView>(R.id.mTitle)
        val mbKc = v.findViewById<TextView>(R.id.mSubtitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.nameFo.text = newList.foodName
        holder.mbKc.text = newList.foodKcal
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}