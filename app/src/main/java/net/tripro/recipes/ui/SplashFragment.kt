package net.tripro.recipes.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.tripro.recipes.R
import net.tripro.recipes.base.BaseFragment


class SplashFragment : BaseFragment() {
    var SPLASH_DISPLAY_LENGTH : Long = 2000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        handler()
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    private fun handler() {
        Handler().postDelayed({
            getNavController()?.navigate(R.id.recipesListFragment)
        }, SPLASH_DISPLAY_LENGTH)
    }
}