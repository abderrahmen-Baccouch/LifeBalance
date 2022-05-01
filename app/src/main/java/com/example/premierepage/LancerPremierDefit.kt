package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class LancerPremierDefit : AppCompatActivity() {
    lateinit var newArray : IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancer_premier_defit)


        setSupportActionBar(findViewById(R.id.toolBar))
         newArray = intArrayOf(
            R.id.bow_pose,
            R.id.bridge_pose,
            R.id.chair_pose,
            R.id.child_pose,
            R.id.cobbler_pose,
            R.id.cow_pose,
            R.id.playji_pose,
            R.id.pauseji_pose,
            R.id.plank_pose,
            R.id.crunches_pose,
            R.id.situp_pose,
            R.id.rotation_pose,
            R.id.twist_pose,
            R.id.windmill_pose,
            R.id.legup_pose
        )

    }


    fun Imagebuttononclicked(view: View) {
        for (i in 0 until newArray.size) {
            if(view.getId() == newArray[i]){
                val value = i + 1
                 Log.i("First", value.toString())
                val intent = Intent(this,calculePremierDefit::class.java)
                intent.putExtra("value",value.toString())
                startActivity(intent)
        }
        }

    }
}