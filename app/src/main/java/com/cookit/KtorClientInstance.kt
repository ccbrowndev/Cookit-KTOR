package com.cookit

import com.cookit.dto.Recipe
import com.cookit.service.RecipeSerializationService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*


object KtorClientInstance {

    private var ktor: HttpClient? = null
    private val recipeDeserializer = RecipeSerializationService()
    private val customGSON: Gson = GsonBuilder()
        .registerTypeAdapter(Recipe::class.java, recipeDeserializer)
        .create()

    val ktorInstance : HttpClient?
        get() {
            if (ktor == null) {
                ktor = HttpClient(CIO){
                    install(ContentNegotiation) {
                        gson { customGSON }
                    }
                }
            }
            return ktor
        }
}