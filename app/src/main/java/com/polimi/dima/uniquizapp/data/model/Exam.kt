package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date

@Parcelize
data class Exam(
    val id: String,
    val subjectId: String,
    val date: Date
    ) : Parcelable
