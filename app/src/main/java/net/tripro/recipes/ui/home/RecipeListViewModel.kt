package net.tripro.recipes.ui.home

import android.widget.Filter
import android.widget.Filterable
import android.widget.Switch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.tripro.recipes.R
import net.tripro.recipes.models.RecipesResponse
import net.tripro.recipes.repos.RecipeRepo
import net.tripro.recipes.utils.AppConstants
import java.util.*
import kotlin.collections.ArrayList

class RecipeListViewModel : ViewModel(), Filterable {

    var recipesList: List<RecipesResponse> = ArrayList()
    var tempRecipesList: List<RecipesResponse> = ArrayList()
    var recipesLiveData: MutableLiveData<List<RecipesResponse>> = MutableLiveData()


    fun getAllRecipe(): MutableLiveData<List<RecipesResponse>> {
        recipeRepository = RecipeRepo.getInstance()!!
        return recipeRepository.getAllRecipe()
    }

    companion object {
        lateinit var recipeRepository: RecipeRepo
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    tempRecipesList = recipesList
                } else {
                    val resultList = ArrayList<RecipesResponse>()
                    for (row in recipesList) {
                        if (row.name?.toLowerCase(Locale.ROOT)!!.contains(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    tempRecipesList = resultList
                }
                filterResults.values = tempRecipesList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                tempRecipesList = results?.values as List<RecipesResponse>
                recipesLiveData.value = tempRecipesList
            }

        }
    }

    fun setRecipeList(it: List<RecipesResponse>) {
        recipesList = it
    }

    fun getFilteredList(): MutableLiveData<List<RecipesResponse>> {
        return recipesLiveData
    }

    fun setSortType(sortType: String) {
        var sortedList: List<RecipesResponse> = ArrayList()
        tempRecipesList = recipesList
        if (sortType.equals(AppConstants.SORT_TYPE_CALORIES)){
            sortedList = tempRecipesList.sortedBy { it.calories }
        }else if (sortType.equals(AppConstants.SORT_TYPE_FATS)){
            sortedList = tempRecipesList.sortedBy { it.fats }
        }
        recipesLiveData.value = sortedList
    }
}