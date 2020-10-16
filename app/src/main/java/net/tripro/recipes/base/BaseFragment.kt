package net.tripro.recipes.base

import android.app.Dialog
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import net.tripro.recipes.R
import net.tripro.recipes.data.local.prefrence.AppPreferences


open class BaseFragment : Fragment() {
    private var navController: NavController? = null
    private var appPreferences: AppPreferences? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController =
            activity?.let { Navigation.findNavController(it, R.id.navigationHostFragment) }
        appPreferences = AppPreferences(context)
    }

    fun getNavController(): NavController? {
        return navController
    }

    fun getAppPreference(): AppPreferences?{
        return appPreferences
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    open fun showConnectionSnackBar(): Snackbar? {
        val content: View = requireActivity().findViewById(android.R.id.content)
        val snackbar =
            Snackbar.make(content, R.string.msg_error_connection, Snackbar.LENGTH_LONG)
        snackbar.show()
        return snackbar
    }

}