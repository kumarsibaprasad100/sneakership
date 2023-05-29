package com.example.sneakersship.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class SneakerData(

    @field:SerializedName("Response")
    val response: List<ResponseItem?>? = null
)


data class Media(

    @field:SerializedName("smallImageUrl")
    val smallImageUrl: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("thumbUrl")
    val thumbUrl: String? = null
)


data class ResponseItem(

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("releaseDate")
    val releaseDate: String? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("styleId")
    val styleId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("colorway")
    val colorway: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("media")
    val media: Media? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("retailPrice")
    val retailPrice: Int? = null,

    @field:SerializedName("shoe")
    val shoe: String? = null
)
