package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*


interface LoginApi {

    @Headers(
        "Content-Type:application/json",
        "IMSI:357175048449937",
        "IMEI:510110406068589"
    )
    @POST()

    fun getLoginDataAsync(
        @Url url: String,
        @Body body: String
    ):
            Deferred<Response<LoginResponse>>

}