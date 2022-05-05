package com.cookit

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cookit.dto.RecipeList
import com.cookit.service.KtorRecipeService
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class RecipeIntegrationTest {

    @Mock
    private lateinit var application: Application

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var recipeService : KtorRecipeService
    private var allRecipes : RecipeList? = RecipeList()


    @Test
    fun `Given recipe data is available When I hit the API Then results should be non-empty` () = runTest {
        givenRecipeServiceIsInitialized()
        whenRecipeDataIsParsed()
        thenRecipeCollectionShouldBeNonEmpty()
    }

    @Test
    fun `Given recipe data is available When I search for Apple Then results should contain Apple Frangipan Tart` () = runTest {
        givenRecipeServiceIsInitialized()
        whenRecipeDataIsParsed()
        thenResultsShouldContainAppleFrangipanTart()
    }


    private fun givenRecipeServiceIsInitialized() {
        recipeService = KtorRecipeService(application)
    }

    private suspend fun whenRecipeDataIsParsed() {
        allRecipes = recipeService.fetchRecipes()
    }

    private fun thenRecipeCollectionShouldBeNonEmpty() {
        assertNotNull(allRecipes)
        assertTrue(allRecipes!!.recipes.isNotEmpty())
    }

    private fun thenResultsShouldContainAppleFrangipanTart() {
        assertNotNull(allRecipes)
        assertTrue(allRecipes!!.recipes.isNotEmpty())
        var containsAppleFrangipanTart = false
        allRecipes?.let {
            it.recipes.forEach {
                if (it.name.lowercase().contains("apple") && it.cuisine == "British") {
                    containsAppleFrangipanTart = true
                }
            }
        }
        assertTrue(containsAppleFrangipanTart)
    }
}