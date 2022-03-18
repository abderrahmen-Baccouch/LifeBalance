package com.example.premierepage

import com.example.premierepage.models.AlimentModel
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface RetrofitInterface {
    @POST("/user/signup")
    fun executeSignup(@Body map: HashMap<String?, String?>?): Call<Void?>?


    @POST("/user/signin")
    fun executeLogin(@Body map: HashMap<String?, String?>?): Call<LoginResult?>?


    @PATCH("user/save")
    fun executeSave(
        @Body map: HashMap<String?, String?>?,
        @Header("authorization") authHeader: String?
    ): Call<Void?>?


    @GET("/aliment/allAliments")
    fun getAliments():Call<ArrayList<AlimentModel>>



}