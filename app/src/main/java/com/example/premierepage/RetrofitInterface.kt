package com.example.premierepage

import com.example.premierepage.model.*
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface RetrofitInterface {
    /**User*/
    @POST("/user/signup")
    fun executeSignup(@Body map: HashMap<String?, String?>?): Call<Void?>?


    @POST("/user/signin")
    fun executeLogin(
        @Body map: HashMap<String?, String?>?
    ): Call<LoginResult?>?


    @PATCH("user/save")
    fun executeSave(
        @Body map: HashMap<String?, String?>?,
        @Header("authorization") authHeader: String?
    ): Call<Void?>?

    @POST("/user/logout")
    fun executeLogout(): Call<Void>?



    /**Aliment*/
    @POST("aliment/addAliment")
    fun executeAddAliment(
        @Body map:HashMap<String?,String?>?,
        @Header("authorization") authHeader: String?
    ):Call<Void?>?

    @GET("aliment/allAliments")
    fun executeAllAliments(
        @Header("authorization") authHeader: String?
    ):Call<MutableList<Aliments>>

    @PUT("aliment/updateAliment/{id}")
    fun executeUpdateAliment(
        @Body map:HashMap<String?,String?>?,
        @Path("id") id:String?
    ):Call<Void?>?

    @DELETE("aliment/delete/{id}")
    fun executeDeleteAliment(@Path("id") id:String?):Call<Void?>?

    @GET("notrealiment/allAliments")
    fun executeAllNotreAliments():Call<MutableList<Aliments>>

    /**Exercice*/

    @GET("exercice/allExercices")
    fun executeAllExercices():Call<MutableList<Exercices>>

    /**ExerciceTermine*/
    @POST("exercicetermine/addExerciceTermine")
    fun executeAddExerciceTermine(
        @Header("authorization") authHeader: String?,
        @Body map:HashMap<String?,String?>?
    )
    :Call<Void?>?


    /**Repas*/

    @GET("recette/allRecettes")
    fun executeAllRecettes(
        @Header("authorization") authHeader: String?)
    :Call<MutableList<RecetteX>>

    @POST("recette/addRecette")
    fun executeAddRecette(
        @Body map:HashMap<String?,String?>?,
        @Header("authorization") authHeader: String?)
    :Call<Void>

}