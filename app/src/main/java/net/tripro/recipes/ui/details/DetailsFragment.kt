package net.tripro.recipes.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import net.tripro.recipes.R
import net.tripro.recipes.base.BaseFragment
import net.tripro.recipes.databinding.FragmentDetailsBinding
import net.tripro.recipes.ui.home.RecipeListViewModel


class DetailsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(DetailsViewModel::class.java)
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        mBinding.executePendingBindings()
        setupUI()
        return mBinding.root
    }

    private fun setupUI() {
        val fromBundle = DetailsFragmentArgs.fromBundle(requireArguments())
        val selectedRcipe = fromBundle.selectedRecipe
        mBinding.recipes = selectedRcipe
        mBinding.recipeNameToolbar.text = selectedRcipe.name
        mBinding.backBtn.setOnClickListener { getNavController()?.popBackStack() }
    }

}