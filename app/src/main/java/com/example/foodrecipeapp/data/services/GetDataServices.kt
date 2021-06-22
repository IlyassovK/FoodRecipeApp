package com.example.foodrecipeapp.data.services

import com.example.foodrecipeapp.data.entities.Category
import com.example.foodrecipeapp.data.entities.Meal
import com.example.foodrecipeapp.data.entities.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface GetDataServices {
    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getMealDetail(@Query("i") id: String): Call<MealResponse>
}