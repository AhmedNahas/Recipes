package net.tripro.recipes.data.remote

import net.tripro.recipes.models.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    @GET("recipes.json")
    fun getAllRecipes() : Call<List<RecipesResponse>>

}