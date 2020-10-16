package net.tripro.recipes.repos

import androidx.lifecycle.MutableLiveData
import net.tripro.recipes.MainActivity
import net.tripro.recipes.models.RecipesResponse

class RecipeRepo {

    fun getAllRecipe(): MutableLiveData<List<RecipesResponse>> {
        return MainActivity.retrofitRepository.getAllRecipes()
    }

    companion object {
        private var recipeRepo: RecipeRepo? = null

        @Synchronized
        fun getInstance(): RecipeRepo? {
            if (recipeRepo == null) {
                recipeRepo = RecipeRepo()
            }
            return recipeRepo
        }
    }
}