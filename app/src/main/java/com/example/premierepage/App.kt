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
        var sexe=myshared?.getString("sexe","")
        var role=myshared?.getString("role","")
        if (token==""){
            val it=Intent(this@App,MainActivity::class.java)
            startActivity(it)
        }else if (token!="" && role=="null" && sexe==""){
            Toast.makeText(this,"aaaa",Toast.LENGTH_LONG)
            val it=Intent(this@App,ThirdActivity::class.java)
            startActivity(it)
        }else if (token!="" && role=="null" && sexe!=""){
            val it=Intent(this@App,FifthActivity::class.java)
            startActivity(it)
        }else if(token!="" && role=="1"){
            val it=Intent(this@App,PageAdmin::class.java)
            startActivity(it)
        }
    }
}