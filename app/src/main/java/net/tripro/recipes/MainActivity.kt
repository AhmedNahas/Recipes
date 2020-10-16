package net.tripro.recipes

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import net.tripro.recipes.data.remote.RetrofitRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitRepository = RetrofitRepository.getInstance(this)!!
    }


    companion object {

        lateinit var retrofitRepository: RetrofitRepository

    }


}