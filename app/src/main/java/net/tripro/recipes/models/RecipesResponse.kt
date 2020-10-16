package net.tripro.recipes.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipesResponse (
    @SerializedName("calories") val calories: String?
    , @SerializedName("carbos") val carbos: String?
    , @SerializedName("description") val description: String?
    , @SerializedName("difficulty") var difficulty: Long?
    , @SerializedName("fats") var fats: String?
    , @SerializedName("headline") var headline: String?
    , @SerializedName("id") var id: String?
    , @SerializedName("image") var image: String?
    , @SerializedName("proteins") var proteins: String?
    , @SerializedName("thumb") var thumb: String?
    , @SerializedName("time") var time: String?
    , @SerializedName("name") var name: String?
) : Parcelable {
}
