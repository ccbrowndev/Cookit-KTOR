package com.cookit.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cookit.dto.Recipe

@Dao
interface IRecipeDAO {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: ArrayList<Recipe>)

    @Delete
    fun delete(recipe: Recipe)
}