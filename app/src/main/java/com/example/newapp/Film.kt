package com.example.newapp

data class Film(
    val id: Int,
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    val people: String,
    val species: String,
    val release_date: Int,
    val rt_score: Int,
    val location: String,
    val url: String
)