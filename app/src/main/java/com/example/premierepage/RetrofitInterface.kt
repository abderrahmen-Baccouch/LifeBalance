package com.example.premierepage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
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

}