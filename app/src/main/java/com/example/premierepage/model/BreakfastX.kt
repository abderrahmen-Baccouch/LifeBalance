package com.example.premierepage.model

data class BreakfastX(
    val __v: Int,
    val _id: String,
    val calories: Int,
    val recettes: MutableList<RecetteX>,
    val caloriesConsome:Int
)