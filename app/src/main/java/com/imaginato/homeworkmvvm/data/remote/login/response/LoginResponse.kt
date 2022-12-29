package com.imaginato.homeworkmvvm.data.remote.login.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("errorCode")
    var errorCode : String,
    @SerializedName("errorMessage")
    var errorMessage : String,
    @SerializedName("data")
    var data : Data

)

data class Data (

    @SerializedName("userId")
    var userId : String,
    @SerializedName("userName")
    var userName : String,
    @SerializedName("isDeleted")
    var isDeleted : Boolean

)
