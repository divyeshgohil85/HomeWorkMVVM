package com.imaginato.homeworkmvvm.data.remote.login

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface LoginRepository {

    suspend fun getLoginData(s: String, s1: String, paramObject: JSONObject): Flow<Any>

}