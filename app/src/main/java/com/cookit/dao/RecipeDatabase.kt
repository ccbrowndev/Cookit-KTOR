package com.cookit.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cookit.dto.Recipe
import com.cookit.service.StringMapConverter

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(StringMapConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): IRecipeDAO
}