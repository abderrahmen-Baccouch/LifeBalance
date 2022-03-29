package com.example.premierepage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.UserData
import com.example.premierepage.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RecipesFragment : Fragment(R.layout.fragment_recipes){
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter

    @SuppressLint("UseRequireInsteadOfGet", "NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = view!!.findViewById(R.id.addingBtn)
        recv = view!!.findViewById(R.id.mRecycler)
        /**set Adapter*/

        userAdapter = UserAdapter(context!!,userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(activity)
        recv.adapter = userAdapter
        /**set Dialog*/

        addsBtn.setOnClickListener {


            val inflter = LayoutInflater.from(this.context)
            val v = inflter.inflate(R.layout.add_item,null)

            /**set view*/
            val userName = v.findViewById<EditText>(R.id.userFood)
            val userNo = v.findViewById<EditText>(R.id.userKcal)
            val userPro = v.findViewById<EditText>(R.id.proteines)
            val userGlu = v.findViewById<EditText>(R.id.glucides)
            val userLip = v.findViewById<EditText>(R.id.lipides)
            val addDialog = AlertDialog.Builder(this.context)

            addDialog.setView(v)
            addDialog.setPositiveButton("ADD"){
                    dialog,_->
                val names = userName.text.toString()
                val number = userNo.text.toString()
                val p = userPro.text.toString()
                val g = userGlu.text.toString()
                val l = userLip.text.toString()

                userList.add(UserData("$names","$number  Kcal ","$p g","$g g","$l g"))
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            addDialog.setNegativeButton("CANCEL"){
                    dialog,_->
                dialog.dismiss()

            }
            addDialog.create()
            addDialog.show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_recipes,container,false)

    }


}


