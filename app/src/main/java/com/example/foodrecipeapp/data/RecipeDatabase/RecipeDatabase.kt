package com.example.foodrecipeapp.data.RecipeDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipeapp.data.dao.RecipeDao
import com.example.foodrecipeapp.data.entities.*
import com.example.foodrecipeapp.data.entities.converters.CategoryListConverter
import com.example.foodrecipeapp.data.entities.converters.MealListConverter

@Database(entities = [Recipe::class, Category::class, CategoryItems::class, Meal::class, MealItems::class], version = 1, exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{

        var recipeDatabase: RecipeDatabase ?= null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if(recipeDatabase == null){
                recipeDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
            }

            return recipeDatabase!!
        }

    }

    abstract fun recipeDao(): RecipeDao
}