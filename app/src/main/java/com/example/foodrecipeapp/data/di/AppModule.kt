package com.example.foodrecipeapp.data.di

import android.content.Context
import com.example.foodrecipeapp.data.adapters.MainCategoryAdapter
import com.example.foodrecipeapp.data.adapters.SubCategoryAdapter
import com.example.foodrecipeapp.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMainRepository(@ApplicationContext context: Context) = MainRepository(context)

    @Singleton
    @Provides
    fun provideMainCategoryAdapter() = MainCategoryAdapter()


    @Singleton
    @Provides
    fun provideSubCategoryAdapter() = SubCategoryAdapter()
}