package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String,
    var username: String,
    var email: String,
    var password: String,
    val firstName: String,
    val lastName: String,
    var universityId: String,
    val subjectIds: List<String>,
    val exams: List<String>,
    val schedules: List<String>,
    val profilePicUrl: String
) : Parcelable