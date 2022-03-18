package com.example.premierepage

import android.content.Intent
import android.graphics.Color.*
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_fifth.*
import java.text.DecimalFormat

class FifthActivity : AppCompatActivity() {

var startPoint = 0
    var endPoint = 0


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

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

         val poids = intent.getStringExtra("poids").toString().toDouble()
         val hauteur = intent.getStringExtra("hauteur").toString().toDouble()
         val cAge = intent.getStringExtra("age").toString().trim()
         val sexe = intent.getStringExtra("sexe").toString().trim()


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
    }
}