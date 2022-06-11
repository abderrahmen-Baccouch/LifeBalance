package com.example.premierepage.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.premierepage.R
import com.example.premierepage.RetrofitClient
import com.example.premierepage.RetrofitInterface
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.Eau
import com.example.premierepage.model.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskAdapter(val c: Context, val taskList:MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.UserViewHolder> () {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null



    inner  class UserViewHolder(val v: View):RecyclerView.ViewHolder(v) {
        var nomTaskTV: TextView
        var descTaskTV: TextView
        var dateTaskTV: TextView
        var timeTask:TextView
        var deleteTask:ImageView

        //  var mMenus : ImageView

        init {
            nomTaskTV = v.findViewById(R.id.nomTask)
            descTaskTV = v.findViewById(R.id.descTask)
            dateTaskTV = v.findViewById(R.id.dateTask)
            timeTask = v.findViewById(R.id.timeTask)
            deleteTask=v.findViewById(R.id.deleteTask)
            //   mMenus = v.findViewById(R.id.mMenus)
            //   mMenus.setOnClickListener { popupMenus(it) }
            val retrofit = RetrofitClient.getInstance()
            retrofitInterface = retrofit.create(RetrofitInterface::class.java)

            deleteTask.setOnClickListener {
                val position = taskList[adapterPosition]
                AlertDialog.Builder(c)
                    .setTitle("Supprimer")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage("Étes-vous certain de vouloir supprimer cette information")
                    .setPositiveButton("Oui") { dialog, _ ->
                        val call = retrofitInterface!!.executeDeleteTask(position._id)
                        call!!.enqueue(object : Callback<Void?> {
                            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                if (response.code() == 200) {
                                    Toast.makeText(c, "Successfuly deleted Task", Toast.LENGTH_LONG).show()
                                } else if (response.code() == 400) {
                                    Toast.makeText(c, "errrror", Toast.LENGTH_LONG)
                                }
                            }
                            override fun onFailure(call: Call<Void?>, t: Throwable) {
                                Toast.makeText(c, t.message, Toast.LENGTH_LONG).show()
                            }

                        })
                        taskList.removeAt(adapterPosition)
                        notifyDataSetChanged()
                        Toast.makeText(c, "Deleted This Information", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Non") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                true


            }
        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_reminder,parent,false)

        return UserViewHolder(v)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val newList = taskList[position]
        holder.nomTaskTV.text = newList.nomTask
        holder.descTaskTV.text = newList.descTask
        holder.dateTaskTV.text=newList.dateTask.subSequence(0,10)
        holder.timeTask.text=newList.dateTask.subSequence(12,16)


    }
    override fun getItemCount(): Int {
        return taskList.size
    }


}