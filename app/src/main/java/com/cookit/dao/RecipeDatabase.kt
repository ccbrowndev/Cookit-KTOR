package com.cookit.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cookit.dto.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): IRecipeDAO
}