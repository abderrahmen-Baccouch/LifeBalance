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
import com.example.premierepage.model.UserData

class UserAdapter (val c:Context,val userList:ArrayList<UserData>) : RecyclerView.Adapter<UserAdapter.UserViewHolder> () {

    inner  class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        var nameFo : TextView
        var mbKc : TextView
        var pro : TextView
        var glu : TextView
        var lip : TextView
        var mMenus : ImageView


        init {
             nameFo = v.findViewById<TextView>(R.id.mTitle)
             mbKc = v.findViewById<TextView>(R.id.mSubtitle)
             pro = v.findViewById<TextView>(R.id.mProteines)
             glu = v.findViewById<TextView>(R.id.mGlucides)
             lip = v.findViewById<TextView>(R.id.mLipides)
             mMenus = v.findViewById(R.id.mMenus)
             mMenus.setOnClickListener { popupMenus(it) }
        }

        @SuppressLint("DiscouragedPrivateApi", "NotifyDataSetChanged")
        private fun popupMenus(v:View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText ->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.userFood)
                        val number = v.findViewById<EditText>(R.id.userKcal)
                        val proNb = v.findViewById<EditText>(R.id.proteines)
                        val gluNb = v.findViewById<EditText>(R.id.glucides)
                        val lipNb = v.findViewById<EditText>(R.id.lipides)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                dialog,_->
                                position.foodName = name.text.toString()
                                position.foodKcal = number.text.toString()+"  Kcal"
                                position.proteinesNb = proNb.text.toString()+" g"
                                position.glucidesNb = gluNb.text.toString()+" g"
                                position.lipidesNb = lipNb.text.toString()+" g"
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information Is Edited",Toast.LENGTH_SHORT).show()
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
                                userList.removeAt(adapterPosition)
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
        val newList = userList[position]
        holder.nameFo.text = newList.foodName
        holder.mbKc.text = newList.foodKcal
        holder.pro.text = newList.proteinesNb
        holder.glu.text = newList.glucidesNb
        holder.lip.text = newList.lipidesNb
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}