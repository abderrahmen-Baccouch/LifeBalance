package com.example.premierepage


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FourthActivity : AppCompatActivity() {
    lateinit var handler : Handler
    var zoom: Animation? = null
    var img: ImageView? = null
    var myanimation: Animation? = null
    var myanimation2: Animation? = null


    private lateinit var wel : TextView
    private lateinit var learning : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        wel = findViewById(R.id.textView1)
        learning = findViewById(R.id.textView2)
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },5000)
        myanimation = AnimationUtils.loadAnimation(applicationContext,R.anim.animation2);
        wel.startAnimation(myanimation)

         myanimation2 = AnimationUtils.loadAnimation(applicationContext,R.anim.animation1);
        learning.startAnimation(myanimation2);



        /**   handler = Handler()0
        handler.postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)*/
     /**2  zoom = AnimationUtils.loadAnimation(applicationContext,R.anim.zoom)
        img = findViewById(R.id.image)
        with(img) { this!!.startAnimation(zoom) }

        val h = Handler()
        h.postDelayed({
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 5000)*/


    }
}