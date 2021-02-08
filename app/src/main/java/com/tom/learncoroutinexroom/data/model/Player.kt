package com.tom.learncoroutinexroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val rank: Int = 0,
    val country: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val age: Int = 0,
    val points: Long,
    var isFavorite : Boolean = false
) : Parcelable