package com.example.premierepage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color.*
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Exercices
import com.example.premierepage.view.ExerciceAdapter
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_fifth.*
import kotlinx.android.synthetic.main.activity_workout_goal.*
import kotlinx.android.synthetic.main.activity_workout_goal.buttonDatePicker
import kotlinx.android.synthetic.main.item_exercice.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class workoutGoal : AppCompatActivity() {
    private var cYear: Int? = null
    private var cMonth: Int? = null
    private var cDay: Int? = null
    private var retrofitInterface: RetrofitInterface? = null
    private lateinit var recv: RecyclerView

    var myshared: SharedPreferences?=null
    //var arr = emptyArray<Int>()
    //val tab = arrayOf (Int)
    val arr = intArrayOf(0)
    val tab = ArrayList<Int>()
    private var nbr1: Int? = 0
    private var nbr2: Int? = 0
    private var nbr3: Int? = 0
    private lateinit var KcalNbr : TextView
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_goal)
      KcalNbr=findViewById(R.id.KcalNbr)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        recv = findViewById(R.id.exercicesRecycler)//esm recycler badlou choufou fil workoutGoal
        val calendar: TextView =findViewById(R.id.calendar)
        val intent = intent
        val nbrKcal1 = intent.getIntExtra("nbrKcal1",0)
        val name = intent.getStringExtra("name").toString()
        /**-------------------------------------myshared-------------------------------------------------- */
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
       // val nbrKcal2 = intent.getStringExtra("nbrKcal2").toString()
        //val name2 = intent.getStringExtra("name2").toString()
      //  val names = name




        val pattern = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(Date())
        calendar.text=date



getExercices(token!!,calendar.text.toString())


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
                getExercices(token,calendar.text.toString())
                // age.text= "${cAge.toString()}"

            },year,month,day)

            //Show dialog
            dpd.show()
        }


/*tab.add(nbrKcal1)
for(index in 0..tab.size-1){
    nbr2 = tab[index] + nbr2!!
}
        val c = nbr2.toString()
        //calerie.text = c
        KcalNbr.text = c*/





/**    if (arr[0]==0){
    arr.set(0,number)
    }else{
       var i = 1
        var b =false
     while(b==false){
         if(arr[i]==null){
            arr.set(i,number)
             b=true
         }
            else{
            i++ }}}

         for (j in 0..arr.size-1){
             val a = nbr1
             nbr1 = a!! +  arr.get(j) }
        val c = nbr1.toString()

*/











      // nbr1 = nbrKcal1
       //nbr2 = nbrKcal2
       //  kcalNbr = findViewById(R.id.KcalNbr)
          //    nbr3 = nbr1!! + nbr2!!
         //   kcalNbr.text = " $nbr3   Kcal"




        progress_circular_workout.apply {
            progress = 0f
            setProgressWithAnimation(100f,3000)
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
    }

    fun getExercices(token:String,dateUser:String){

        val map = HashMap<String?, String?>()
        map["dateUser"] = dateUser
        val call = retrofitInterface!!.executeAllExerciceTermine(map,token)
        call.enqueue(object : retrofit2.Callback<MutableList<Exercices>> {
            override fun onResponse(call: Call<MutableList<Exercices>>, response: Response<MutableList<Exercices>>) {
                if (response.code()==200){
                    var listExerciceTermine=response.body()
                    var calConsome=0
                    for(i in 0..listExerciceTermine!!.size-1) {
                        Toast.makeText(this@workoutGoal,i.toString(),Toast.LENGTH_LONG).show()
                        calConsome=calConsome+listExerciceTermine[i].calories.toInt()
                    }

                    KcalNbr.text= calConsome.toString()
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@workoutGoal)
                        adapter= ExerciceAdapter(this@workoutGoal,response.body()!!,object:
                            ExerciceAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {

                            }
                        }/*object:ExerciceAdapter.onItemClickListener{
                           override fun onItemClick(position: Int) {}}*/)
                    }
                }else if (response.code()==400){
                }
            }
            override fun onFailure(call: Call<MutableList<Exercices>>, t: Throwable) {
                Toast.makeText(this@workoutGoal, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}