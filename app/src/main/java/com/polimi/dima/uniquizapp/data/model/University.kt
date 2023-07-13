package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class University (
    val id: String,
    val name: String,
    val location: String,
    val subjectIds: List<String>) : Parcelable {
}