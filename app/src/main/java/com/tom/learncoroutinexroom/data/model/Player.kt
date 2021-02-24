package com.tom.learncoroutinexroom.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

open class Player : RealmObject() {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var gender: String? = null
    var rank: Int? = 0
    var country: String? = null
    var description: String? = null
    var imageUrl: String? = null
    var age: Int? = 0
    var points: Long? = 0
    var isFavorite: Boolean? = false
}