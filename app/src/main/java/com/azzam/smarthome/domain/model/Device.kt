package com.azzam.smarthome.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Device(
    val title: String? = null,
    val image: Int? = null,
): Parcelable
