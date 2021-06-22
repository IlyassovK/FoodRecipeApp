package com.example.foodrecipeapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipeapp.data.entities.CategoryItems
import com.example.foodrecipeapp.data.entities.MealItems
import com.example.foodrecipeapp.data.entities.MealResponse
import com.example.foodrecipeapp.data.entities.MealsEntity
import com.example.foodrecipeapp.data.repository.MainRepository
import com.plcoding.spotifycloneyt.other.Event
import com.plcoding.spotifycloneyt.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _mainCategoryList = MutableLiveData<List<CategoryItems>>()
    val mainCategoryList: LiveData<List<CategoryItems>> = _mainCategoryList

    private val _subCategoryList = MutableLiveData<List<MealItems>>()
    val subCategoryList: LiveData<List<MealItems>> = _subCategoryList

    private val _mealDetails = MutableLiveData<Resource<MealsEntity>>()
    val mealDetails: LiveData<Resource<MealsEntity>> = _mealDetails

    fun clearDb() = repository.clearDatabase()

    fun getCategories() = repository.getCategories()

    fun getSpecifiedMealList(categoryName: String){
        CoroutineScope(Job() + Dispatchers.IO).launch {
            val mealItems =  repository.getSpecifiedMealListFromDb(categoryName)

            _subCategoryList.postValue(mealItems)
        }

    }

    suspend fun getSpecifiedMealDetail(id: String){
        repository.getSpecifiedMeal(id)
        _mealDetails.postValue(repository.selectedMealDetail.value)
    }

    fun getDataFromDb(){
        CoroutineScope(Job() + Dispatchers.IO).launch {
            val categoryList = repository.getAllCategoryDataFromDb()

            getSpecifiedMealList(categoryList.last().strcategory)

            _mainCategoryList.postValue(categoryList)
        }
    }
}