package com.tom.learncoroutinexroom.data.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Player(
    @PrimaryKey var _ID: Long? = 0,
    var firstName: String? = null,
    var lastName: String? = null,
    var gender: String? = null,
    var rank: Int? = 0,
    var country: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var age: Int? = 0,
    var points: Long? = 0,
    var isFavorite: Boolean? = false
) : RealmObject(), Parcelable {}