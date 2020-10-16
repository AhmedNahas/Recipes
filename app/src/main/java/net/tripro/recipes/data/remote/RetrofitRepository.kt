package net.tripro.recipes.data.remote

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.tripro.recipes.models.RecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepository private constructor() {

    private var apiRequests: ApiRequests

    companion object {
        private var retrofitRepository: RetrofitRepository? = null
        private var context: Context? = null

        @Synchronized
        fun getInstance(context: Context?): RetrofitRepository? {
            this.context = context
            if (retrofitRepository == null) {
                retrofitRepository = RetrofitRepository()
            }
            return retrofitRepository
        }
    }

    init {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiRequests = retrofit.create(ApiRequests::class.java)
    }

    fun getAllRecipes(): MutableLiveData<List<RecipesResponse>> {
        val data: MutableLiveData<List<RecipesResponse>> = MutableLiveData()
        apiRequests.getAllRecipes().enqueue(object : Callback<List<RecipesResponse>> {
            override fun onResponse(
                call: Call<List<RecipesResponse>>,
                response: Response<List<RecipesResponse>>
            ) {
                if (response.isSuccessful) {
                    Log.d("getAllRecipes", "success")
                    data.value = response.body()
                } else {
                    Log.d("getAllRecipes", "failed")
                }
            }
            override fun onFailure(call: Call<List<RecipesResponse>>, t: Throwable) {
                Log.e("getAllRecipes", "error" + " " + t.message)
            }
        })
        return data
    }
}