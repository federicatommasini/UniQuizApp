package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import com.google.api.client.util.DateTime
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date

@Parcelize
data class ExamRequest (
    val subjectId: String,
    val date : String
): Parcelable