package net.tripro.recipes.ui.home

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import net.tripro.recipes.R
import net.tripro.recipes.base.BaseFragment
import net.tripro.recipes.databinding.FragmentRecipesListBinding
import net.tripro.recipes.models.RecipesResponse
import net.tripro.recipes.utils.AppConstants


class RecipesListFragment : BaseFragment(), RecipeListAdapter.OnItemClick {

    private lateinit var sortType: String
    private lateinit var viewModel: RecipeListViewModel
    private lateinit var mBinding: FragmentRecipesListBinding
    lateinit var adapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(RecipeListViewModel::class.java)
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recipes_list, container, false)
        mBinding.viewModel = viewModel
        mBinding.executePendingBindings()
        setupUI()
        subscripeToLiveData()
        return mBinding.root
    }

    private fun setupUI() {
        //spinkit setup a styled loading view
        val progressBar: ProgressBar = mBinding.spinKit
        val doubleBounce: Sprite = Wave()
        progressBar.indeterminateDrawable = doubleBounce

        //search in recipesList using textWatcher
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filter.filter(s)
            }
        })

        //when press back finish app
        val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        sortType = getAppPreference()!!.getSortType()
        Log.d("sortType",sortType.toString())


        //sorting list
        mBinding.sort.setOnClickListener { showSortingDialog() }

        //setting adapter
        adapter = RecipeListAdapter(ArrayList(), this)

        //refresh
        mBinding.retryBtn.setOnClickListener{
            mBinding.cLSpinKit.visibility = View.VISIBLE
            subscripeToLiveData()}
    }

    private fun subscripeToLiveData() {

        //check internet connection before fetching data
        if (isNetworkAvailable(context)) {
            viewModel.getAllRecipe().observe(viewLifecycleOwner, Observer {
                Log.d("subscripeToLiveData", it.size.toString())
                mBinding.sort.visibility = View.VISIBLE
                viewModel.setRecipeList(it)
                mBinding.cLSpinKit.visibility = View.GONE
                adapter.setList(it)
                mBinding.rvRecipes.adapter = adapter
                //if user has chosen a sort type before , will apply it after list fetched
                if (sortType != "null"){
                    viewModel.setSortType(sortType)
                }
            })
            viewModel.getFilteredList().observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            })
            //if not connected will display a snackbar
        } else {
            mBinding.cLSpinKit.visibility = View.GONE
            mBinding.noInternetLinear.visibility = View.VISIBLE
            showConnectionSnackBar()
        }

    }

    fun showSortingDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        val calories = dialog.findViewById(R.id.tv_calories) as TextView
        val fats = dialog.findViewById(R.id.tv_fats) as TextView
        calories.setOnClickListener {
            getAppPreference()?.setSortType(AppConstants.SORT_TYPE_CALORIES)
            viewModel.setSortType(AppConstants.SORT_TYPE_CALORIES)
            dialog.dismiss()
        }
        fats.setOnClickListener {
            getAppPreference()?.setSortType(AppConstants.SORT_TYPE_FATS)
            viewModel.setSortType(AppConstants.SORT_TYPE_FATS)
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onRecipeClick(recipe: RecipesResponse) {
        val actionRecipesListFragmentToDetailsFragment =
            RecipesListFragmentDirections.actionRecipesListFragmentToDetailsFragment(recipe)
        getNavController()?.navigate(actionRecipesListFragmentToDetailsFragment)
    }


}