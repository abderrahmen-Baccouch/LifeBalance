package com.example.premierepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.ExerciceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotreAlimentsFragment : Fragment(R.layout.fragment_notrealiments) {
    private var retrofitInterface: RetrofitInterface? = null

    private lateinit var recv: RecyclerView

    @SuppressLint("UseRequireInsteadOfGet", "NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       recv = view!!.findViewById(R.id.mRecycler)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)


        val call = retrofitInterface!!.executeAllNotreAliments()
        call.enqueue(object : Callback<MutableList<Aliments>> {
            override fun onResponse(call: Call<MutableList<Aliments>>, response: Response<MutableList<Aliments>>) {
                if (response.code()==200){
                    Toast.makeText(context, "aaaaa", Toast.LENGTH_LONG).show()
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(activity)
                        adapter= AlimentAdapter(context,response.body()!!,object: AlimentAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val i = Intent(context,FifthActivity::class.java)

                            }
                        })
                    }
                }else if (response.code()==400){
                    Toast.makeText(context, "400", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Aliments>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })

    }


















    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notrealiments, container, false)
    }
}
