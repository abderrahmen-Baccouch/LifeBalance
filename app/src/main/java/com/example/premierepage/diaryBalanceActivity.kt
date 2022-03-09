package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.view.View
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


        retour.setOnClickListener {
            val intent = Intent(this,FifthActivity::class.java)
            startActivity(intent)
        }
    }
}