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
import com.example.premierepage.model.Aliments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlimentAdapter(val c: Context, val alimentsList:MutableList<Aliments>,val mListener:onItemClickListener) : RecyclerView.Adapter<AlimentAdapter.UserViewHolder> () {
    private var retrofitInterface: RetrofitInterface? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }


    inner  class UserViewHolder(val v: View,listener:onItemClickListener):RecyclerView.ViewHolder(v){
        var nomAlimentTV: TextView
        var caloriesTV : TextView
        var proteinesTV : TextView
        var glucidesTV : TextView
        var lipidesTV : TextView
        var imageIV:ImageView
        var mMenus : ImageView
        init {
            nomAlimentTV = v.findViewById<TextView>(R.id.nomAliment)
            caloriesTV = v.findViewById<TextView>(R.id.calories)
            proteinesTV = v.findViewById<TextView>(R.id.proteines)
            glucidesTV = v.findViewById<TextView>(R.id.glucides)
            imageIV=v.findViewById<ImageView>(R.id.imageAliment)
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

            val position = alimentsList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText ->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)

                        val nomAlimentET = v.findViewById<EditText>(R.id.nomAliment)
                        val caloriesET = v.findViewById<EditText>(R.id.calories)
                        val proteinesET = v.findViewById<EditText>(R.id.proteines)
                        val glucidesET = v.findViewById<EditText>(R.id.glucides)
                        val lipidesET = v.findViewById<EditText>(R.id.lipides)

                        nomAlimentET.setText(position.nomAliment)
                        caloriesET.setText(position.calories)
                        proteinesET.setText(position.proteines)
                        glucidesET.setText(position.glucides)
                        lipidesET.setText(position.lipides)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Update"){
                                dialog,_->
                                position.nomAliment = nomAlimentET.text.toString()
                                position.calories = caloriesET.text.toString()+"  Kcal"
                                position.proteines = proteinesET.text.toString()+" g"
                                position.glucides = glucidesET.text.toString()+" g"
                                position.lipides = lipidesET.text.toString()+" g"
                                val map = HashMap<String?, String?>()

                                map["nomAliment"] = nomAlimentET.text.toString()
                                map["calories"] = caloriesET.text.toString()
                                map["proteines"] =proteinesET.text.toString()
                                map["glucides"] = glucidesET.text.toString()
                                map["lipides"] = lipidesET.text.toString()
                               val call = retrofitInterface!!.executeUpdateAliment(map,position._id)
                               call!!.enqueue(object : Callback<Void?>{
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
                                val call = retrofitInterface!!.executeDeleteAliment(position._id)
                                call!!.enqueue(object :Callback<Void?>{
                                    override fun onResponse(
                                        call: Call<Void?>,
                                        response: Response<Void?>
                                    ) {
                                        if (response.code()==200){
                                            Toast.makeText(c,"Successfuly deleted aliment",Toast.LENGTH_LONG).show()
                                        }else if (response.code()==400){
                                            Toast.makeText(c,"errrror",Toast.LENGTH_LONG)
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
                                Toast.makeText(c,"Deleted This Information",Toast.LENGTH_SHORT).show()
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



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_aliment,parent,false)
        return UserViewHolder(v,mListener)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = alimentsList[position]
        holder.nomAlimentTV.text = newList.nomAliment
        holder.caloriesTV.text = newList.calories+" Kcal"
        holder.proteinesTV.text = newList.proteines+" g"
        holder.glucidesTV.text = newList.glucides+" g"
        holder.lipidesTV.text = newList.lipides+" g"
        Glide.with(c).load(newList.imageURL).into(holder.imageIV)

    }
    override fun getItemCount(): Int {
        return alimentsList.size
    }


}