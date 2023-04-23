package com.example.githubuserverme.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    val username : String,
    val image : String,
    val name : String,
    val location : String
):Parcelable
