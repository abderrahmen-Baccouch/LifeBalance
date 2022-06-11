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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EauAdapter(val c: Context, val eauList:MutableList<Eau>) : RecyclerView.Adapter<EauAdapter.UserViewHolder> () {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null



    inner  class UserViewHolder(val v: View):RecyclerView.ViewHolder(v) {
        var quantiteTV: TextView
        var objectifTV: TextView
        var dateEauTV: TextView
        var cancelEau:ImageView

        //  var mMenus : ImageView

        init {
            quantiteTV = v.findViewById(R.id.quantite)
            objectifTV = v.findViewById(R.id.objectif)
            dateEauTV = v.findViewById(R.id.dateEau)
            cancelEau = v.findViewById(R.id.cancelEau)
            //   mMenus = v.findViewById(R.id.mMenus)
            //   mMenus.setOnClickListener { popupMenus(it) }
            val retrofit = RetrofitClient.getInstance()
            retrofitInterface = retrofit.create(RetrofitInterface::class.java)

            cancelEau.setOnClickListener {
                val position = eauList[adapterPosition]
                AlertDialog.Builder(c)
                    .setTitle("Delete")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage("Are You Sure To Delete This Information")
                    .setPositiveButton("Yes") { dialog, _ ->
                        val call = retrofitInterface!!.executeDeleteEau(position._id)
                        call!!.enqueue(object : Callback<Void?> {
                            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                if (response.code() == 200) {
                                    Toast.makeText(c, "Successfuly deleted Eau", Toast.LENGTH_LONG).show()
                                } else if (response.code() == 400) {
                                    Toast.makeText(c, "errrror", Toast.LENGTH_LONG)
                                }
                            }

                            override fun onFailure(call: Call<Void?>, t: Throwable) {
                                Toast.makeText(c, t.message, Toast.LENGTH_LONG).show()
                            }

                        })
                        eauList.removeAt(adapterPosition)
                        notifyDataSetChanged()
                        Toast.makeText(c, "Deleted This Information", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                true


            }
        }



       /* R.id.delete
        {
            AlertDialog.Builder(c)
                .setTitle("Delete")
                .setIcon(R.drawable.ic_warning)
                .setMessage("Are You Sure To Delete This Information")
                .setPositiveButton("Yes") { dialog, _ ->
                    val call = retrofitInterface!!.executeDeleteAliment(position._id)
                    call!!.enqueue(object : Callback<Void?> {
                        override fun onResponse(
                            call: Call<Void?>,
                            response: Response<Void?>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(c, "Successfuly deleted aliment", Toast.LENGTH_LONG)
                                    .show()
                            } else if (response.code() == 400) {
                                Toast.makeText(c, "errrror", Toast.LENGTH_LONG)
                            }
                        }

                        override fun onFailure(call: Call<Void?>, t: Throwable) {
                            Toast.makeText(
                                c, t.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                    alimentsList.removeAt(adapterPosition)
                    notifyDataSetChanged()
                    Toast.makeText(c, "Deleted This Information", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
            true
        }





        }*/
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_eau,parent,false)

        return UserViewHolder(v)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val newList = eauList[position]
        holder.quantiteTV.text = newList.quantite.toString()+" L"
        holder.objectifTV.text = newList.objectif.toString()+" L"
        holder.dateEauTV.text=newList.createAt


    }
    override fun getItemCount(): Int {
        return eauList.size
    }


}