package com.example.premierepage

class LoginResult {
    private val token: String? = null
    private val username:String?=null
    private val role:Int?=null


    fun getToken(): String? {
        return token
    }

    fun getRole(): Int? {
        return role
    }

    fun getUsername(): String? {
        return username
    }

    private val message: String? = null

    fun getMessage(): String? {
        return message
    }


}