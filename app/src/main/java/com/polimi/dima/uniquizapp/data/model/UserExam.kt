package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserExam (
    val exam : Exam,
    val notes : String?
    ): Parcelable{
}