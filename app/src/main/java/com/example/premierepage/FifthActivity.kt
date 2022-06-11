package com.example.premierepage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.BreakfastX
import com.example.premierepage.model.Exercices
import com.example.premierepage.view.NotreRepasAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_fifth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class FifthActivity : AppCompatActivity() {
    private var cYear: Int? = null
    private var cMonth: Int? = null
    private var cDay: Int? = null
    var myshared: SharedPreferences?=null
    lateinit var toggle : ActionBarDrawerToggle
    var startPoint = 0
    var endPoint = 0
    private var retrofitInterface: RetrofitInterface? = null
    private lateinit var recv: RecyclerView
    private lateinit var recv2: RecyclerView
    private lateinit var recv3: RecyclerView
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }
    private var clicked = false
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)
        /**----------------------------------------Retrofit and myshared ------------------------------------------*/
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        val token =myshared?.getString("token","").toString().trim()
        val poids=myshared?.getString("poids","").toString().toDouble()
        val hauteur =myshared?.getString("hauteur","").toString().toDouble()
        val age =myshared?.getString("age","").toString().trim()
        val sexe =myshared?.getString("sexe","").toString().trim()

        bouton_eau.setOnClickListener {
            val intent = Intent(this,ConsommationEau::class.java)
            startActivity(intent)
        }
        recv = findViewById(R.id.listeAlimentRecycler)
        recv2 = findViewById(R.id.listeAlimentRecycler2)
        recv3 = findViewById(R.id.listeAlimentRecycler3)
        getRecettes(token,calendar.text.toString())
        getRecettes2(token,calendar.text.toString())
        getRecettes3(token,calendar.text.toString())

        val pattern = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(Date())
        calendar.text=date

        getCaloriesConsome(token,calendar.text.toString())
        getCaloriesBrulee(token,calendar.text.toString())
        val cc=caloriesConsome.text.toString().toInt()
        val cb=caloriesBrulee.text.toString().toInt()
        tv_stepsTaken.text=(cc-cb).toString() //problem d asyncronization

        fab.setOnClickListener {
            onAddButtonClicked()
        }

        edit_btn.setOnClickListener {
            Toast.makeText(this,"Edit Buton Clicked",Toast.LENGTH_SHORT).show()

            val intent = Intent(this,personnelSetting::class.java)
            val a= poids.toString()
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
                    startActivity(intent)
                }
                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> {
                    /**---------------Logout----------*/
                    var editor: SharedPreferences.Editor=myshared!!.edit()
                    editor.remove("token")
                    editor.commit()
                    Toast.makeText(applicationContext,"Clicked LogOut",Toast.LENGTH_SHORT).show()
                    val i2 = Intent(this,App::class.java)
                    startActivity(i2)
                    finish()
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
                    val intent = Intent(this,reminder::class.java)
                    startActivity(intent)
                }
                R.id.home -> Toast.makeText(this,"home",Toast.LENGTH_SHORT).show()
                R.id.recipe -> {
                    Toast.makeText(this,"recettes",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,Nutrition::class.java)
                    startActivity(intent)
                }
                R.id.setting -> {
                    Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show()
                  //  val intent = Intent(this,DietType::class.java)
                    val intent = Intent(this,ThirdActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }



        //get data from intent
        val intent = intent
        val dec = DecimalFormat("#,###.00")



        imc.text = dec.format(poids/(hauteur*hauteur)).toString()
if(poids/(hauteur*hauteur)<=18.5){
    imc.setTextColor(Color.parseColor("#33F3FF"));
    imcDescription.setTextColor(Color.parseColor("#33F3FF"))
    imcDescription.setText("Maigreur")
}else if(poids/(hauteur*hauteur)>18.5 && poids/(hauteur*hauteur)<=24.9){
    imc.setTextColor(Color.parseColor("#336EFF"));
    imcDescription.setTextColor(Color.parseColor("#336EFF"))
    imcDescription.setText("Normal")
}else if (poids/(hauteur*hauteur)>24.9 && poids/(hauteur*hauteur)<=29.9){
    imc.setTextColor(Color.parseColor("#FFDD33"));
    imcDescription.setTextColor(Color.parseColor("#FFDD33"))
    imcDescription.setText("Surpoids")
}else if (poids/(hauteur*hauteur)>29.9 && poids/(hauteur*hauteur)<=40){
    imc.setTextColor(Color.parseColor("#FF7733"));
    imcDescription.setTextColor(Color.parseColor("#FF7733"))
    imcDescription.setText("Obésité")
}else {
    imc.setTextColor(Color.parseColor("#F11313"));
    imcDescription.setTextColor(Color.parseColor("#F11313"))
    imcDescription.setText("Obésité massive")
}




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
            val b = age.toString()
            intent.putExtra("age",b)
            intent.putExtra("sexe",a)
            startActivity(intent)
        }
        /* fab.setOnClickListener{
             val intent = Intent(this,workoutGoal::class.java)
             startActivity(intent)
         }*/












        addBreakFast.setOnClickListener {
            val i3 = Intent(this,Nutrition::class.java)
            startActivity(i3)
        }

        addLunch.setOnClickListener {
            val i3 = Intent(this,Nutrition::class.java)
            startActivity(i3)
        }
        addDinner.setOnClickListener {
            val i3 = Intent(this,Nutrition::class.java)
            startActivity(i3)
        }
        //Calendar
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        //button click to show DatePicker
        buttonDatePicker.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { view, mYear, mMonth, mDay ->
                day = mDay
                month = mMonth
                year = mYear
                //set to textView
                cYear=year
                cMonth=month+1
                cDay=day
                calendar.text ="${cDay.toString().padStart(2, '0')}-${cMonth.toString().padStart(2, '0')}-${cYear.toString()}"
                // age.text= "${age.toString()}"
                getCaloriesConsome(token,calendar.text.toString())
                getCaloriesBrulee(token,calendar.text.toString())
                getRecettes(token,calendar.text.toString())
                getRecettes2(token,calendar.text.toString())
                getRecettes3(token,calendar.text.toString())
                tv_stepsTaken.text=(caloriesConsome.text.toString().toInt()-caloriesBrulee.text.toString().toInt()).toString()
                var editor: SharedPreferences.Editor=myshared!!.edit()
                editor.putString("dateUser", calendar.text.toString())
                editor.commit()
            },year,month,day)

            //Show dialog
            dpd.show()
        }
    }
    fun getCaloriesConsome(token:String,dateUser:String){
        val map = HashMap<String?, String?>()
        map["dateUser"] = dateUser

        val call = retrofitInterface!!.executeGetCaloriesConsome(map,token)
        call.enqueue(object : Callback<BreakfastX> {
            override fun onResponse(call: Call<BreakfastX>, response: Response<BreakfastX>) {
                if (response.code()==200){
                    caloriesConsome.text=response.body()!!.caloriesConsome.toString()
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<BreakfastX>, t: Throwable) {
                Toast.makeText(this@FifthActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    fun getCaloriesBrulee(token:String,dateUser:String){
        val map = HashMap<String?, String?>()
        map["dateUser"] = dateUser

        val call = retrofitInterface!!.executeGetCaloriesBrulee(map,token)
        call.enqueue(object : Callback<Exercices> {
            override fun onResponse(call: Call<Exercices>, response: Response<Exercices>) {
                if (response.code()==200){
                    caloriesBrulee.text=response.body()!!.caloriesBrulee.toString()
                }else if (response.code()==400){

                }
            }
            override fun onFailure(call: Call<Exercices>, t: Throwable) {
                Toast.makeText(this@FifthActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })

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
    fun getRecettes(token:String,dateUser:String){
        val map = HashMap<String?, String?>()
        map["dateUser"] = dateUser//dateUser
        val call = retrofitInterface!!.executeAllBreakfast(token,map)
        call.enqueue(object : retrofit2.Callback<MutableList<BreakfastX>> {
            override fun onResponse(call: Call<MutableList<BreakfastX>>, response: Response<MutableList<BreakfastX>>) {
                if (response.code()==200){
                    val listExercice=response.body()
                    if (listExercice!!.size!=0) {
                        recv.apply {
                            recv.layoutManager = LinearLayoutManager(this@FifthActivity)
                            adapter = NotreRepasAdapter(this@FifthActivity, response.body()!![0]!!.recettes, object : NotreRepasAdapter.onItemClickListener {
                                    override fun onItemClick(position: Int) {

                                    }
                                }/*object:ExerciceAdapter.onItemClickListener{
                           override fun onItemClick(position: Int) {}}*/
                            )
                        }
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<BreakfastX>>, t: Throwable) {
                Toast.makeText(this@FifthActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    fun getRecettes2(token:String,dateUser:String){
        val map = HashMap<String?, String?>()
        map["dateUser"] = dateUser
        val call = retrofitInterface!!.executeAllLunch(token,map)
        call.enqueue(object : retrofit2.Callback<MutableList<BreakfastX>> {
            override fun onResponse(call: Call<MutableList<BreakfastX>>, response: Response<MutableList<BreakfastX>>) {
                if (response.code()==200){
                    val listExercice=response.body()
                    if (listExercice!!.size!=0){
                    recv2.apply {
                        recv2.layoutManager = LinearLayoutManager(this@FifthActivity)
                        adapter= NotreRepasAdapter(this@FifthActivity,response.body()!![0]!!.recettes,object:NotreRepasAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {

                            }
                        })
                    }
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<BreakfastX>>, t: Throwable) {
                Toast.makeText(this@FifthActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    fun getRecettes3(token:String,dateUser:String){
        val map = HashMap<String?, String?>()
        map["dateUser"] = dateUser
        val call = retrofitInterface!!.executeAllDinner(token,map)
        call.enqueue(object : retrofit2.Callback<MutableList<BreakfastX>> {
            override fun onResponse(call: Call<MutableList<BreakfastX>>, response: Response<MutableList<BreakfastX>>) {
                if (response.code()==200){
                    val listExercice=response.body()
                    if (listExercice!!.size!=0){
                    recv3.apply {
                        recv3.layoutManager = LinearLayoutManager(this@FifthActivity)
                        adapter = NotreRepasAdapter(
                            this@FifthActivity,
                            response.body()!![0]!!.recettes,
                            object : NotreRepasAdapter.onItemClickListener {
                                override fun onItemClick(position: Int) {

                                }
                            }
                        )
                    }
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<BreakfastX>>, t: Throwable) {
                Toast.makeText(this@FifthActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        myshared=getSharedPreferences("myshared",0)
        val token =myshared?.getString("token","").toString().trim()
        getRecettes(token,calendar.text.toString())
        getRecettes2(token,calendar.text.toString())
        getRecettes3(token,calendar.text.toString())

    }

}