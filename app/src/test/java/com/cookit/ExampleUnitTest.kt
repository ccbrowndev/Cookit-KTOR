package com.cookit

import junit.framework.Assert.*
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var recipeService : RecipeService
    var allRecipes : Set<Recipe>? = HashSet<Recipe>()


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `Given recipe data is available When I search for Japanese Then I should receive Tonkotsu Ramen` () = runTest {
        givenRecipeServiceIsInitialized()
        whenRecipeDataIsParsed()
        thenRecipeCollectionShouldContainTonkotsuRamen()
    }

    private fun givenRecipeServiceIsInitialized() {
        recipeService = RecipeService
    }

    private suspend fun whenRecipeDataIsParsed() {
        allRecipes = recipeService.getPlants()
    }

    private fun thenRecipeCollectionShouldContainTonkotsuRamen() {
        assertNotNull(allRecipes)
        assertTrue(allRecipes!!.isNotEmpty())
        var containsTonkotsuRamen = false
        allRecipes!!.forEach {
            if (it.cuisine.equals(("Japanese")) && it.name.equals("Tonkotsu Ramen")) {
                containsTonkotsuRamen = true
            }
        }
        assertTrue(containsTonkotsuRamen)
    }


}