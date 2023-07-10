package com.polimi.dima.uniquizapp.data.model

import android.os.Parcelable
import com.google.api.client.util.DateTime
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date

@Parcelize
@Serializable
data class Exam(
    val id: String,
    val subjectId: String,
    @Serializable(with = DateSerializer::class)
    //@JsonAdapter(DateSerializer::class)
    val date: Date,
) : Parcelable
