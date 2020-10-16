package net.tripro.recipes.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recipes_item.view.*
import net.tripro.recipes.R


class BindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("setImage")
        fun setImage(imageView: ImageView, url: String?) {
            val context: Context = imageView.getContext()
            Glide.with(context).load(url).placeholder(R.drawable.splash_icon).into(imageView)
        }
    }


}