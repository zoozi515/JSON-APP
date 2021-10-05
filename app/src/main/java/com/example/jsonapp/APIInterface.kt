package com.example.jsonapp

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET ("eur.json")
    fun doGetListRecources(): Call<CurrencyDetails?>?
}