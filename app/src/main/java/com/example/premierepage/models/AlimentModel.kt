package com.example.premierepage.models

public class AlimentModel {
    private var nomAliment: String? = null


    fun AlimentModel(al: String?) {
        this.nomAliment = al

    }

    fun getnomAliment(): String? {
        return nomAliment
    }




}