package com.example.premierepage.model

data class Ingredient(
    val _id: String,
    val aliment: Aliments,
    val calories: Int,
    val proteines: Int,
    val glucides: Int,
    val lipides: Int,
    val quantite: Int
)