package com.example.premierepage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.*
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
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

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }
    private var clicked = false
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

         fab.setOnClickListener {
             onAddButtonClicked()
         }
         edit_btn.setOnClickListener {
             Toast.makeText(this,"Edit Buton Clicked",Toast.LENGTH_SHORT).show()
             val poids =myshared?.getString("poids","").toString().trim()
             val hauteur =myshared?.getString("hauteur","").toString().toDouble()
             val age =myshared?.getString("age","").toString().trim()
             val sexe =myshared?.getString("sexe","").toString().trim()
             val intent = Intent(this,personnelSetting::class.java)
             val a = poids.toString()
             val b = hauteur.toString()
             val c = age.toString()
             val d = sexe.toString()
             intent.putExtra("poids",a)
             intent.putExtra("hauteur",b)
             intent.putExtra("age",c)
             intent.putExtra("sexe",d)
             startActivity(intent)
         }
         workout.setOnClickListener {
             val intent = Intent(this,Defit3::class.java)
             startActivity(intent)
         }
         image_btn.setOnClickListener {
             Toast.makeText(this,"Image Button Clicked",Toast.LENGTH_SHORT).show()
             val intent = Intent(this,DietType::class.java)
             startActivity(intent)
         }





        /* navView1.setNavigationItemSelectedListener {
             when(it.itemId){
                 R.id.rappel -> Toast.makeText(this,"rappel",Toast.LENGTH_SHORT).show()
             }
             true
         }*/

         val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
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
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    Toast.makeText(applicationContext,"Home",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,FifthActivity::class.java)
                    startActivity(intent)}

                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> {
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
        /* defit_4.setOnClickListener {
             val intent = Intent(this,dietPlans::class.java)
             startActivity(intent)
         }*/

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
        /* fab.setOnClickListener{
             val intent = Intent(this,workoutGoal::class.java)
             startActivity(intent)
         }*/
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

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            edit_btn.startAnimation(fromBottom)
            image_btn.startAnimation(fromBottom)
            workout.startAnimation(fromBottom)
            fab.startAnimation(rotateOpen)
        }else{
            edit_btn.startAnimation(toBottom)
            image_btn.startAnimation(toBottom)
            workout.startAnimation(toBottom)
            fab.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            edit_btn.visibility = View.VISIBLE
            image_btn.visibility = View.VISIBLE
            workout.visibility = View.VISIBLE
        }else{
            edit_btn.visibility = View.INVISIBLE
            image_btn.visibility = View.INVISIBLE
            workout.visibility = View.INVISIBLE
        }
    }
    private fun setClickable(clicked: Boolean){
        if(!clicked){
            edit_btn.isClickable = true
            image_btn.isClickable = true
            workout.isClickable = true
        }else{
            edit_btn.isClickable = false
            image_btn.isClickable = false
            workout.isClickable = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}