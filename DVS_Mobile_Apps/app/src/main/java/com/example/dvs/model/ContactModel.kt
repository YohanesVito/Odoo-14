package com.example.dvs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewContactModel(
    val uid: String,
    val email: String,
    val avatar: String,
    val tokenFCM: String,
): Parcelable
