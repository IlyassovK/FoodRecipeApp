package com.example.foodrecipeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipeapp.data.entities.Category
import com.example.foodrecipeapp.data.entities.CategoryItems
import com.example.foodrecipeapp.data.entities.MealItems
import com.example.foodrecipeapp.data.entities.Recipe

@Dao
interface RecipeDao {

    @get:Query("SELECT * FROM categoryitems ORDER BY id DESC")
    val getAllCategory: List<CategoryItems>

    @get:Query("SELECT * FROM mealitems ORDER BY id DESC")
    val getAllMeal: List<MealItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: MealItems)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

    @Query("SELECT * FROM mealitems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecifiedMealList(categoryName: String): List<MealItems>

}