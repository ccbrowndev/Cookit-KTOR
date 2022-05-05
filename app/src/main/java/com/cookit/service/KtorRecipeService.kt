package com.cookit.service

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.room.Room
import com.cookit.KtorClientInstance
import com.cookit.dao.IRecipeDAO
import com.cookit.dao.RecipeDatabase
import com.cookit.dto.Recipe
import com.cookit.dto.RecipeList
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val BASE_URL = "https://www.themealdb.com/api/json/v2/"
private val CALLS = mapOf("allRecipes" to "9973533/search.php?s=")

interface IRecipeService {
    suspend fun fetchRecipes(call: String = "allRecipes"): RecipeList?
    fun getRecipeDAO(): IRecipeDAO
}

class KtorRecipeService(val application: Application) : IRecipeService {
    lateinit var db: RecipeDatabase

    override suspend fun fetchRecipes(call: String): RecipeList? {
        return withContext(Dispatchers.IO) {
            val response = KtorClientInstance.ktorInstance?.get(BASE_URL + CALLS[call])
                ?: throw Exception("Error!, ktorClient was null for some reason!")
            if (response.status.value in 200..299) {
                updateRecipes(response.body())
                return@withContext response.body()
            } else {
                throw Exception("Failed to get recipes. Server Response: ${response.status.value}")
            }
        }
    }

    private fun updateRecipes(recipeList: RecipeList?) {
        try {
            val recipes = recipeList?.recipes ?: arrayListOf()
            recipes.let {
                val recipeDao = getRecipeDAO()
                recipeDao.insertAll(recipes)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving recipes")
        }
    }

    override fun getRecipeDAO(): IRecipeDAO {
        if (!this::db.isInitialized) {
            db = Room.databaseBuilder(
                application,
                RecipeDatabase::class.java, "recipe-db"
            ).build()
        }
        return db.recipeDao()
    }
}