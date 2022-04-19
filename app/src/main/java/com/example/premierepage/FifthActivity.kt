package com.example.premierepage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.*
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.api.AnnotationsProto.http
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

         /* navView1.setNavigationItemSelectedListener {
             when(it.itemId){
                 R.id.rappel -> Toast.makeText(this,"rappel",Toast.LENGTH_SHORT).show()
             }
             true
         }*/

         val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        // val navHeader=findViewById<LinearLayout>(R.id.navH)
         val imgMenu = findViewById<ImageView>(R.id.imgMenu)
         val navView = findViewById<NavigationView>(R.id.navDrawar)



         navView.itemIconTintList = null
         imgMenu.setOnClickListener {
             drawerLayout.openDrawer(GravityCompat.START)
         }

           toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
           drawerLayout.addDrawerListener(toggle)
           toggle.syncState()

      supportActionBar?.setDisplayHomeAsUpEnabled(true)

         //   Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
         //   Toast.makeText(this, usernameTV.text, Toast.LENGTH_SHORT).show()
        navView.setNavigationItemSelectedListener {

            val usernameTV = findViewById<TextView>(R.id.usernameTVV)
            myshared=getSharedPreferences("myshared",0)
            var username =myshared?.getString("username","")
            usernameTV.setText(username)

            when(it.itemId){
                R.id.nav_home -> {
                    Toast.makeText(applicationContext,"Home",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,FifthActivity::class.java)
                    startActivity(intent)}

                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> {
                    /**********logout***********************/
                    myshared?.edit()!!.remove("token").commit()
                    val i=Intent(this,App::class.java)
                    startActivity(i)
                    Toast.makeText(applicationContext,"Clicked LogOut",Toast.LENGTH_SHORT).show()

                }
                R.id.nav_share -> {
                    Toast.makeText(applicationContext,"Share",Toast.LENGTH_SHORT).show()
                    val intent = Intent (Intent.ACTION_SEND)
                    intent.type = "type/palin"
                    val sharedBody = "You are body"
                    val sharedSub = "Diet & Fitenss"
                    intent.putExtra(Intent.EXTRA_SUBJECT , sharedBody)
                    intent.putExtra(Intent.EXTRA_TEXT , sharedSub)
                    startActivity(Intent.createChooser(intent , "Share your app"))
                }
                R.id.nav_rate_us -> {
                    Toast.makeText(applicationContext,"Rate Us",Toast.LENGTH_SHORT).show()
                    try{
                        val uri : Uri = Uri.parse("market://details?id= ${getPackageName()}")
                        val intent = Intent (Intent.ACTION_VIEW,uri)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                }catch (activityNotFound : ActivityNotFoundException){
                        val uri : Uri = Uri.parse("http://play.google.com/store/apps/details?id=${getPackageName()}")
                        val intent = Intent (Intent.ACTION_VIEW,uri)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                    }
                }

            }
           true
         }
         val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
         bottomNavigationView.setOnNavigationItemSelectedListener {
             when(it.itemId){
                 R.id.rappel -> {
                     Toast.makeText(this,"rappel",Toast.LENGTH_SHORT).show()
                     val cAge =myshared?.getString("poids","").toString().trim()
                     val intent = Intent(this,personnelSetting::class.java)
                     val a = cAge.toString()
                     intent.putExtra("poids",a)
                     startActivity(intent)
                 }
                 R.id.home -> Toast.makeText(this,"home",Toast.LENGTH_SHORT).show()
                 R.id.recipe -> {
                     Toast.makeText(this,"recettes",Toast.LENGTH_SHORT).show()
                     val intent = Intent(this,dietPlans::class.java)
                     startActivity(intent)
                 }
                 R.id.setting -> {
                     Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show()
                     val intent = Intent(this,DietType::class.java)
                     startActivity(intent)
                 }
             }
             true
         }



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
             val intent = Intent(this,Defit1::class.java)
             startActivity(intent)
         }
         defit_2.setOnClickListener {
             val intent = Intent(this,Defit2::class.java)
             startActivity(intent)
         }
         defit_3.setOnClickListener {
             val intent = Intent(this,Defit3::class.java)
             startActivity(intent)
         }
         defit_4.setOnClickListener {
             val intent = Intent(this,dietPlans::class.java)
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
             val intent = Intent(this,ThirdActivity::class.java)
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