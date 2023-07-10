package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

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
    val exams : List<UserExam>,
    val schedules: List<Schedule>,
    val profilePicUrl: String,
    val questionsAdded : Int,
    val questionsReported : Int
) : Parcelable