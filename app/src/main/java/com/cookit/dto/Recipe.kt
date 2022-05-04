package com.cookit.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey @SerializedName("idMeal") var recipeID: String = "",
    @SerializedName("strMeal") var name: String = "",
    @SerializedName("strCategory") var category: String = "",
    @SerializedName("strArea") var cuisine: String = "",
    @SerializedName("strInstructions") var instructions: String = "",
    @ColumnInfo(name = "image_url") @SerializedName("strMealThumb") var imageURL: String = "",
    @ColumnInfo(name = "youtube_url") @SerializedName("strYoutube") var youtubeURL: String = "",
    @Expose var ingredients: MutableMap<String, String> = LinkedHashMap(),
    @Ignore @Transient var fireStoreID: String = "",
) {
    override fun toString(): String {
        return name
    }
}


