package net.tripro.recipes.data.local.prefrence

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context?) {
    private val mPrefs: SharedPreferences
    private val PFER_NAME: String = "recipesPreference"
    private val PREF_KEY_SORTING_TYPE: String = "sort_type"


    fun setSortType(sortType: String?) {
        mPrefs.edit().putString(PREF_KEY_SORTING_TYPE, sortType).apply()
    }

    fun getSortType(): String {
        return mPrefs.getString(PREF_KEY_SORTING_TYPE, null).toString()
    }

    init {
        mPrefs = context!!.getSharedPreferences(PFER_NAME, Context.MODE_PRIVATE)
    }
}