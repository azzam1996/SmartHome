package com.azzam.smarthome.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(
    val title: String,
    val image: Int,
    val devices: List<Device>? = null,
    val temperature: Int,
    val maxTemperature: Int,
    val minTemperature: Int,
): Parcelable
