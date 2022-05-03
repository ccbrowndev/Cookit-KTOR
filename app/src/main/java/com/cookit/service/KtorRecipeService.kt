package com.cookit.service

import com.cookit.KtorClientInstance
import com.cookit.dto.RecipeList
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val BASE_URL = "https://www.themealdb.com/api/json/v2/"
private val CALLS = mapOf("allRecipes" to "9973533/search.php?s=")

interface IRecipeService {
    suspend fun fetchRecipes(call: String = "allRecipes"): RecipeList?
}

class KtorRecipeService : IRecipeService {
    override suspend fun fetchRecipes(call: String): RecipeList? {
        return withContext(Dispatchers.IO) {
            val response = KtorClientInstance.ktorInstance?.get(BASE_URL + CALLS[call])
                ?: throw Exception("Error!, ktorClient was null for some reason!")
            if (response.status.value in 200..299) {
                return@withContext response.body()
            } else {
                throw Exception("Failed to get recipes. Server Response: ${response.status.value}")
            }
        }
    }
}