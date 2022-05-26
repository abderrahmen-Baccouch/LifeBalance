package com.example.premierepage.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.R
import com.example.premierepage.model.Defit

class StepAdapter(val c: Context, val stepList:MutableList<String>) : RecyclerView.Adapter<StepAdapter.StepViewHolder> () {


    class StepViewHolder(v: View): RecyclerView.ViewHolder(v) {
        var stepDescriptionTV: TextView =v.findViewById<TextView>(R.id.stepDescription)
        var stepOrderTV: TextView =v.findViewById<TextView>(R.id.stepOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_step,parent,false)
        return StepViewHolder(v)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val newList = stepList[position]
        holder.stepDescriptionTV.text = newList
        holder.stepOrderTV.text = "Etape"+(position+1).toString()+":"

    }

    override fun getItemCount(): Int {
        return stepList.size
    }
}