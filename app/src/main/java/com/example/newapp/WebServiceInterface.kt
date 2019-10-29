package com.example.newapp

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query


interface WebServiceInterface {

    @GET("character/list")
    fun listAllCharacters(): Call<MutableList<Character>>

    @GET("film/details")
    fun filmDetail(@Query("film_id") id: Int): Call<Film>
}