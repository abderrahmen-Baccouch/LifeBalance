package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color.*
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_fifth.*
import java.text.DecimalFormat
class FifthActivity : AppCompatActivity() {

    var myshared: SharedPreferences?=null
    lateinit var toggle : ActionBarDrawerToggle

var startPoint = 0
    var endPoint = 0


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

       val  drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
         val imgMenu = findViewById<ImageView>(R.id.imgMenu)
        val navView = findViewById<NavigationView>(R.id.navDrawar)
         navView.itemIconTintList = null
         imgMenu.setOnClickListener {
             drawerLayout.openDrawer(GravityCompat.START)
         }

         //    toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
       //  drawerLayout.addDrawerListener(toggle)
      //   toggle.syncState()

      //   supportActionBar?.setDisplayHomeAsUpEnabled(true)
      //   navView.setNavigationItemSelectedListener {
       //      when(it.itemId){
       //          R.id.nav_home -> Toast.makeText(applicationContext,"Clicked Home",Toast.LENGTH_SHORT).show()
       //          R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting",Toast.LENGTH_SHORT).show()
       //          R.id.nav_logout -> Toast.makeText(applicationContext,"Clicked LogOut",Toast.LENGTH_SHORT).show()
       //          R.id.nav_share -> Toast.makeText(applicationContext,"Clicked Share",Toast.LENGTH_SHORT).show()
       //          R.id.nav_rate_us -> Toast.makeText(applicationContext,"Clicked Rate Us",Toast.LENGTH_SHORT).show()

         //    }
          //   true
       //  }



         volumeSeek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
             override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                 seekbar.text = progress.toString()
             }

             override fun onStartTrackingTouch(seekBar: SeekBar?) {

                     if (seekBar != null) {
                         startPoint = seekBar.progress
                     }

             }

             override fun onStopTrackingTouch(seekBar: SeekBar?) {
                 if (seekBar != null) {
                     endPoint = seekBar.progress
                 }
             }

         })

         //get data from intent
         val intent = intent
         val dec = DecimalFormat("#,###.00")
         myshared=getSharedPreferences("myshared",0)
         val poids =myshared?.getString("poids","").toString().toDouble()
         val hauteur =myshared?.getString("hauteur","").toString().toDouble()
         val cAge =myshared?.getString("age","").toString().trim()
         val sexe =myshared?.getString("sexe","").toString().trim()

         imc.text = dec.format(poids/(hauteur*hauteur)).toString()


      progress_circular.apply {
          progress = 100f
          setProgressWithAnimation(30f,3000)
          progressBarWidth = 12f
          backgroundProgressBarWidth = 16f
          progressBarColorStart = CYAN
          progressBarColorEnd = WHITE
          roundBorder = true
          progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
          // Set background ProgressBar Color
          backgroundProgressBarColor = GRAY
          // or with gradient
          backgroundProgressBarColorStart = WHITE
          backgroundProgressBarColorEnd = rgb(220,20,60)
          backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM


      }
         defit_1.setOnClickListener {
             val intent = Intent(this,Defit::class.java)
             startActivity(intent)
         }

         exercice.setOnClickListener {
             val intent = Intent(this,ActivityExercices::class.java)
             startActivity(intent)
         }

         diaryBalance_button.setOnClickListener {
         val intent = Intent(this,diaryBalanceActivity::class.java)
             val a = sexe.toString()
             val b = cAge.toString()
             intent.putExtra("age",b)
             intent.putExtra("sexe",a)
             startActivity(intent)
         }
         fab.setOnClickListener{
             val intent = Intent(this,workoutGoal::class.java)
             startActivity(intent)
         }
         addBreakFast.setOnClickListener {
             val intent = Intent(this,aliments::class.java)
             startActivity(intent)
         }
         addLunch.setOnClickListener {
             val intent = Intent(this,aliments::class.java)
             startActivity(intent)
         }
         addDinner.setOnClickListener {
             val intent = Intent(this,Tennis::class.java)
             startActivity(intent)
         }
    }
   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}