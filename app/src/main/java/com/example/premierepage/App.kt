package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class App:AppCompatActivity() {
    var myshared: SharedPreferences?=null
    override fun onStart() {
        super.onStart()
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")

        if (token!=""){
            //lazem thezni lel FifthActivity ama lmochkla ena fil fifth test7a9 des données mel third
            val it=Intent(this@App,FifthActivity::class.java)

            startActivity(it)
        }else if (token==""){
            val it=Intent(this@App,MainActivity::class.java)
            startActivity(it)
        }
    }
}