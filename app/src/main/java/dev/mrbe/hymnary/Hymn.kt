package dev.mrbe.hymnary

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hymn(
    val title: String = "",
    val hymnNumber: String = "",
    val content: String = "",
    val hymnType: String = ""
) : Parcelable
