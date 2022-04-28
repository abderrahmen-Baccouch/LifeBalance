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

   // @POST("/user/logout")
   // fun executeLogout(): Call<Void>?





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



    /**notre aliment*/
    @GET("notrealiment/allAliments")
    fun executeAllNotreAliments():Call<MutableList<Aliments>>

    @POST("notrealiment/addAliment")//---------------Admin
    fun executeAddNotreAliment(
        @Body map:HashMap<String?,String?>?
    ):Call<Void?>?

    @PUT("notrealiment/updateAliment/{id}")//---------------Admin
    fun executeUpdateNotreAliment(
        @Body map:HashMap<String?,String?>?,
        @Path("id") id:String?
    ):Call<Void?>?

    @DELETE("notrealiment/delete/{id}")//---------------Admin
    fun executeDeleteNotreAliment(@Path("id") id:String?):Call<Void?>?




    /**Exercice*/

    @GET("exercice/allExercices")
    fun executeAllExercices():Call<MutableList<Exercices>>

    //add
    @POST("exercice/addExercice")
    fun executeAddExercice(
        @Body map:HashMap<String?,String?>?)
    :Call<Void>

    //update
    @PUT("exercice/updateExercice/{idexercice}")
    fun executeUpdateExercice(
        @Body map:HashMap<String?,String?>?,
        @Path("idexercice") idexercice:String?
        ):Call<Void>

    //delete

    @DELETE("exercice/delete/{id}")
    fun executeDeleteExercice(
        @Path("id") id:String?
    ):Call<Void>




    /**ExerciceTermine*/
    @POST("exercicetermine/addExerciceTermine")
    fun executeAddExerciceTermine(
        @Header("authorization") authHeader: String?,
        @Body map:HashMap<String?,String?>?
    )
    :Call<Void?>?

    @GET("exercicetermine/allExerciceTermine")
    fun executeAllExerciceTermine(
        @Header("authorization") authHeader: String?
    ):Call<MutableList<Exercices>>


    /**Repas*/

    @GET("recette/allRecettes")
    fun executeAllRecettes(
        @Header("authorization") authHeader: String?)
    :Call<MutableList<RecetteX>>

    @POST("recette/addRecette")
    fun executeAddRecette(
        @Body map:HashMap<String?,String?>?,
        @Header("authorization") authHeader: String?)
    :Call<Recette>

    @POST("recette/addIngredient/{idrecette}/{idaliment}")
    fun executeAddIngredient(@Header("authorization") authHeader: String?,
                             @Path("idrecette") idrecette:String?,
                             @Path("idaliment") idaliment:String?,
                             @Body map:HashMap<String?,String?>?)
    :Call<RecetteX>


    @PUT("recette/save/{idrecette}")
    fun executeSaveRecette(@Path("idrecette") idrecette:String?,)
    :Call<Void>


    @GET("notrerepas/allrepas")
    fun executeAllRepas()
    :Call<MutableList<Repa>>


    /**Diary*/

    @POST("/diary/createBreakfast")
    fun executeCreateBreakfast(
        @Header("authorization") authHeader: String?,)
    :Call<BreakfastX>

    @POST("/diary/createDinner")
    fun executeCreateDinner(
        @Header("authorization") authHeader: String?,)
            :Call<BreakfastX>

    @POST("/diary/createLunch")
    fun executeCreateLunch(
        @Header("authorization") authHeader: String?,)
            :Call<BreakfastX>

    @POST("diary/addBreakfast/{idbreakfast}/{idrecette}")
    fun executeAddBreakfast(@Header("authorization") authHeader: String?,
                            @Path("idbreakfast") idbreakfast:String?,
                            @Path("idrecette") idrecette:String?,)
    :Call<Void>




}