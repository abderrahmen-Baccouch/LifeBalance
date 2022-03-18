package com.example.premierepage

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit {
        if (instance == null)
            instance = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:3000")
                .build()

        return instance!!
    }
}