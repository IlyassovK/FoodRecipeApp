package com.example.foodrecipeapp.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.foodrecipeapp.data.RecipeDatabase.RecipeDatabase
import com.example.foodrecipeapp.data.entities.*
import com.example.foodrecipeapp.data.retrofit.RetrofitInstance
import com.example.foodrecipeapp.data.services.GetDataServices
import com.plcoding.spotifycloneyt.other.Event
import com.plcoding.spotifycloneyt.other.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class MainRepository @Inject constructor (
    @ApplicationContext private val context: Context
){

    var selectedMealDetail = MutableLiveData<Resource<MealsEntity>>()

    init {
        selectedMealDetail.postValue(Resource.loading(null))
    }

    fun getCategories(){
        val service = RetrofitInstance.retrofitInstance!!.create(GetDataServices::class.java)
        val call = service.getCategoryList()

        call.enqueue(object : Callback<Category> {
            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                for (i in response.body()!!.categorieitems!!){
                    getMeals(i.strcategory)
                }
                insertCategoryListIntoDb(response.body())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {

                //todo  hide loader
                Toast.makeText(context, "Unknown error occurred", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMeals(category: String){
        val service = RetrofitInstance.retrofitInstance!!.create(GetDataServices::class.java)
        val call = service.getMealList(category)

        call.enqueue(object : Callback<Meal> {
            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                insertMealListIntoDb(category ,response.body())
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {

                //todo  hide loader
                Toast.makeText(context, "Unknown error occurred", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getSpecifiedMeal(id: String) {
        val service = RetrofitInstance.retrofitInstance!!.create(GetDataServices::class.java)
        val call = service.getMealDetail(id)

        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {
                var mealResponse: MealResponse? = response.body()
                selectedMealDetail.postValue(Resource.success(mealResponse!!.mealsEntity[0]))
            }
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                //todo  hide loader
                Toast.makeText(context, "Unknown error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun insertMealListIntoDb(category: String, meal: Meal?){
        CoroutineScope(Job() + Dispatchers.IO).launch {
            this.let {
                for(i in meal!!.meals!!){
                    val mealItem = MealItems(
                        i.id,
                        i.idMeal,
                        category,
                        i.strMeal,
                        i.strMealThumb
                    )
                    RecipeDatabase.getDatabase(context).recipeDao()
                        .insertMeal(mealItem)
                }
                //todo  show btn getStarted
            }
        }
    }

    fun insertCategoryListIntoDb(category: Category?){
        CoroutineScope(Job() + Dispatchers.IO).launch {
            this.let {
                for(i in category!!.categorieitems!!){
                    RecipeDatabase.getDatabase(context).recipeDao()
                        .insertCategory(i)
                }
                //todo  show btn getStarted
            }
        }
    }

    fun clearDatabase(){
        CoroutineScope(Job() + Dispatchers.IO).launch {
            this.let {
                RecipeDatabase.getDatabase(context).recipeDao().clearDb()
            }
        }
    }

    suspend fun getAllCategoryDataFromDb(): List<CategoryItems> {
        return RecipeDatabase.getDatabase(context).recipeDao().getAllCategory
    }

    suspend fun getSpecifiedMealListFromDb(categoryName: String): List<MealItems>{
        return RecipeDatabase.getDatabase(context).recipeDao().getSpecifiedMealList(categoryName)
    }

    suspend fun getAllMealDataFromDb(): List<MealItems> {
        return RecipeDatabase.getDatabase(context).recipeDao().getAllMeal
    }
}
