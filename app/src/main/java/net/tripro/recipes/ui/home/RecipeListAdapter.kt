package net.tripro.recipes.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipes_item.view.*
import net.tripro.recipes.databinding.RecipesItemBinding
import net.tripro.recipes.models.RecipesResponse

class RecipeListAdapter(private var recipesList: List<RecipesResponse>, fragment: Fragment) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private val clickHandler: OnItemClick = fragment as OnItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mBinding = RecipesItemBinding.inflate(layoutInflater, parent, false)
        return RecipeListViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.bind(recipesList.get(position))
        holder.itemView.recipe_headline.isSelected = true
        holder.itemView.setOnClickListener {
            clickHandler.onRecipeClick(recipesList.get(position))
        }
    }

    fun setList(it: List<RecipesResponse>) {
        recipesList = it
    }

    class RecipeListViewHolder(val binding: RecipesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecipesResponse) {
            binding.recipes = item
            binding.executePendingBindings()
        }
    }
    interface OnItemClick{
        fun onRecipeClick(recipe : RecipesResponse)
    }

}