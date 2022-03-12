package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_diary_balance.*

class diaryBalanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_balance)

        val intent = intent
        val cAge = intent.getStringExtra("age")
        val sexe = intent.getStringExtra("sexe").toString().trim()

        val energie= findViewById<TextView>(R.id.energie)
        val proteine= findViewById<TextView>(R.id.proteine)
        val carb= findViewById<TextView>(R.id.carb)
        val fat= findViewById<TextView>(R.id.fat)

        if (sexe == "HOMME"){
            if (cAge!!.toInt() in 15..19){
                energie.text ="3000 kcal"
                proteine.text="85 g"
                carb.text="375 g"
                fat.text="130 g"
            }
            else if (cAge.toInt() in 20..30){
                energie.text="2700 kcal"
                proteine.text="80 g"
                carb.text="340 g"
                fat.text="120 g"
            }
            else if (cAge.toInt() in 31..50){
                energie.text="260 kcal"
                proteine.text="70 g"
                carb.text="325 g"
                fat.text="80 g"
            }
            else if (50<cAge.toInt()){
                energie.text="2250 kcal"
                proteine.text="60 g"
                carb.text="280 g"
                fat.text="60 g"
            }
        }
        else if (sexe == "FEMME") {
            if (cAge!!.toInt() in 15..19) {
                energie.text = "2400 kcal"
                proteine.text = "75 g"
                carb.text = "300 g"
                fat.text = "90 g"
            } else if (cAge.toInt() in 20..30) {
                energie.text = "2100 kcal"
                proteine.text = "65 g"
                carb.text = "265 g"
                fat.text = "80 g"
            } else if (cAge.toInt() in 31..50) {
                energie.text = "2000 kcal"
                proteine.text = "60 g"
                carb.text = "250 g"
                fat.text = "70 g"
            } else if (50 < cAge.toInt()) {
                energie.text = "1800 kcal"
                proteine.text = "45 g"
                carb.text = "220 g"
                fat.text = "55 g"
            }
        }

        setupPieChart()

    }
    private fun setupPieChart() {
        // Setup Pie Entries
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(30f,"Fat"))
        pieEntries.add(PieEntry(20f,"Protèine"))
        pieEntries.add(PieEntry(50f,"Carb"))

        // Setup Pie Chart Animation
        pieChart.animateXY(1500, 1500) // This 1000 is time that how much time piechart chreated

        // Setup PicChart Colors
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(
            resources.getColor(R.color.pinky),
            resources.getColor(R.color.blu),
            resources.getColor(R.color.green)
        )


        // Setup Pie Data Set in PieData
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true) // This is for values in pie entries.
        pieData.setValueTextColor(R.color.white)
        pieData.setDrawValues(true)
        pieChart.setEntryLabelColor(R.color.white)
        pieChart.setEntryLabelColor(R.color.white)
        pieChart.textAlignment= View.TEXT_ALIGNMENT_CENTER


        pieData.setValueTextSize(11f)
        pieChart.setEntryLabelTextSize(11F)

        pieData.setValueFormatter(PercentFormatter(pieChart))
        pieChart.setUsePercentValues(true)


        // Setup PieCharts Values and Ui
        pieChart.description.isEnabled = false  // This is pie chart description that in below entries.


        // This is because of tags of pie chart entries.
        pieChart.legend.isEnabled = false


        // this is for space that is at the center of the pie chart.
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = 45f

        // Finally Setup the add Values in PieChart.
        pieChart.data = pieData

        goToHome.setOnClickListener {
            val intent = Intent(this,FifthActivity::class.java)
            startActivity(intent)
        }
        retour.setOnClickListener {
            val intent = Intent(this,FifthActivity::class.java)
            startActivity(intent)
        }






    }
}