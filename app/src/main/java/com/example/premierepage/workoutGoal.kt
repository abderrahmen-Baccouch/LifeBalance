package com.example.premierepage

import android.annotation.SuppressLint
import android.graphics.Color.*
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_workout_goal.*

class workoutGoal : AppCompatActivity() {
    //var arr = emptyArray<Int>()
    //val tab = arrayOf (Int)
    val arr = intArrayOf(0)
    val tab = ArrayList<Int>()
    private var nbr1: Int? = 0
    private var nbr2: Int? = 0
    private var nbr3: Int? = 0
    private lateinit var kcalNbr : TextView
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_goal)



        val intent = intent
        val nbrKcal1 = intent.getIntExtra("nbrKcal1",0)
        val name = intent.getStringExtra("name").toString()

       // val nbrKcal2 = intent.getStringExtra("nbrKcal2").toString()
        //val name2 = intent.getStringExtra("name2").toString()
      //  val names = name


tab.add(nbrKcal1)
for(index in 0..tab.size-1){
    nbr2 = tab[index] + nbr2!!
}
        val c = nbr2.toString()
        //calerie.text = c
        KcalNbr.text = c












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
}