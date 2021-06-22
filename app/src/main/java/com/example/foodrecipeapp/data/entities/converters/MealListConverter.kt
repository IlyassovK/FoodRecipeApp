package com.example.foodrecipeapp.data.entities.converters

import androidx.room.TypeConverter
import com.example.foodrecipeapp.data.entities.MealItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealListConverter {
    @TypeConverter
    fun fromMealList(meal: List<MealItems>):String?{
        if (meal == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<MealItems>(){

            }.type
            return gson.toJson(meal,type)
        }
    }

    @TypeConverter
    fun toMealList ( mealString: String):List<MealItems>?{
        if (mealString == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object :TypeToken<MealItems>(){

            }.type
            return  gson.fromJson(mealString,type)
        }
    }
}