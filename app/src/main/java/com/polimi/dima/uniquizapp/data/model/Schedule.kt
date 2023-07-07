package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Schedule(
    val id: String,
    val quizId: String,
    val date: Date,
    val score: Integer
): Parcelable
