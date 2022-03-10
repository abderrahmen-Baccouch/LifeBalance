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

    private lateinit var energie : TextView
    private lateinit var proteine : TextView
    private lateinit var carb : TextView
    private lateinit var fat : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_balance)

        val intent = intent

        val cAge = intent.getStringExtra("age").toString().trim()
        val radio = intent.getStringExtra("radio").toString().trim()

        energie= findViewById(R.id.energie)
        proteine= findViewById(R.id.proteine)
        carb= findViewById(R.id.carb)
        fat= findViewById(R.id.fat)

        if (radio == "HOMME"){
            if (14<cAge.toInt() && cAge.toInt()<20){
                energie.text ="3000kcal"
                proteine.text="85g"
                carb.text="375g"
                fat.text="130g"
            }
            else if (19<cAge.toInt() && cAge.toInt()<31){
                energie.text="2700lcal"
                proteine.text="80g"
                carb.text="340g"
                fat.text="120g"
            }
            else if (30<cAge.toInt() && cAge.toInt()<51){
                energie.text="260kcal"
                proteine.text="70g"
                carb.text="325g"
                fat.text="80g"
            }
            else if (50<cAge.toInt()){
                energie.text="2250kcal"
                proteine.text="60g"
                carb.text="280g"
                fat.text="60g"
            }
        }
        else if (radio == "FEMME") {
            if (14 < cAge.toInt() && cAge.toInt() < 20) {
                energie.text = "2400kcal"
                proteine.text = "75g"
                carb.text = "300g"
                fat.text = "90g"
            } else if (19 < cAge.toInt() && cAge.toInt() < 31) {
                energie.text = "2100kcal"
                proteine.text = "65g"
                carb.text = "265g"
                fat.text = "80g"
            } else if (30 < cAge.toInt() && cAge.toInt() < 51) {
                energie.text = "2000kcal"
                proteine.text = "60g"
                carb.text = "250g"
                fat.text = "70g"
            } else if (50 < cAge.toInt()) {
                energie.text = "1800kcal"
                proteine.text = "45g"
                carb.text = "220g"
                fat.text = "55g"
            }
        }

        setupPieChart()

    }
    private fun setupPieChart() {
        // Setup Pie Entries
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(20f,"Fat"))
        pieEntries.add(PieEntry(40f,"Protèine"))
        pieEntries.add(PieEntry(70f,"Carb"))

        // Setup Pie Chart Animation
        pieChart.animateXY(1500, 1500) // This 1000 is time that how much time piechart chreated

        // Setup PicChart Colors
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.rose),
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