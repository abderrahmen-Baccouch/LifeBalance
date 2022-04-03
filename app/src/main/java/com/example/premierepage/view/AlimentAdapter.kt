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
import com.example.premierepage.model.Aliments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlimentAdapter(val c: Context, val alimentsList:MutableList<Aliments>) : RecyclerView.Adapter<AlimentAdapter.UserViewHolder> () {
    private var retrofitInterface: RetrofitInterface? = null

    inner  class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        var nomAlimentTV: TextView
        var caloriesTV : TextView
        var proteinesTV : TextView
        var glucidesTV : TextView
        var lipidesTV : TextView
        var mMenus : ImageView
        init {
            nomAlimentTV = v.findViewById<TextView>(R.id.nomAliment)
            caloriesTV = v.findViewById<TextView>(R.id.calories)
            proteinesTV = v.findViewById<TextView>(R.id.proteines)
            glucidesTV = v.findViewById<TextView>(R.id.glucides)
            lipidesTV = v.findViewById<TextView>(R.id.lipides)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
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

                        val nomAliment = v.findViewById<EditText>(R.id.nomAliment)
                        val calories = v.findViewById<EditText>(R.id.calories)
                        val proteines = v.findViewById<EditText>(R.id.proteines)
                        val glucides = v.findViewById<EditText>(R.id.glucides)
                        val lipides = v.findViewById<EditText>(R.id.lipides)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Update"){
                                dialog,_->
                                position.nomAliment = nomAliment.text.toString()
                                position.calories = calories.text.toString()+"  Kcal"
                                position.proteines = proteines.text.toString()+" g"
                                position.glucides = glucides.text.toString()+" g"
                                position.lipides = lipides.text.toString()+" g"
                                val map = HashMap<String?, String?>()

                                map["nomAliment"] = nomAliment.text.toString()
                                map["calories"] = calories.text.toString()
                                map["proteines"] =proteines.text.toString()
                                map["glucides"] = glucides.text.toString()
                                map["lipides"] = lipides.text.toString()
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
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = alimentsList[position]
        holder.nomAlimentTV.text = newList.nomAliment
        holder.caloriesTV.text = newList.calories
        holder.proteinesTV.text = newList.proteines
        holder.glucidesTV.text = newList.glucides
        holder.lipidesTV.text = newList.lipides

    }
    override fun getItemCount(): Int {
        return alimentsList.size
    }


}