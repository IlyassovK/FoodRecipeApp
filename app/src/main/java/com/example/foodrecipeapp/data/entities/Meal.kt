package com.example.foodrecipeapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.foodrecipeapp.data.entities.converters.CategoryListConverter
import com.example.foodrecipeapp.data.entities.converters.MealListConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Meal")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "mealItems")
    @Expose
    @SerializedName("meals")
    @TypeConverters(MealListConverter::class)
    val meals: List<MealItems>? = null
)

