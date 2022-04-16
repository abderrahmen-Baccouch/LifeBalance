package com.example.premierepage.model

data class RecetteX(
    val __v: Int,
    val _id: String,
    val calories: Int,
    val ingredients: List<Ingredient>,
    val nomRecette: String,
    val user: String
)