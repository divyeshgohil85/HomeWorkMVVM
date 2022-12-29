package com.imaginato.homeworkmvvm.data.remote.demo

import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url


interface DemoApi {
    @GET
    fun getDemoDataAsync(@Url url: String): Deferred<LoginResponse>
}
