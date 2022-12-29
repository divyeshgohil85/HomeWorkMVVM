package com.imaginato.homeworkmvvm.data.remote.login


import android.content.Context
import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class LoginDataRepository(
    private var api: LoginApi,
    context: Context,
    private  var loginDao: LoginDao
) : LoginRepository {
    companion object {
        private const val URL_GET_PUBLIC_IP =  "http://private-222d3-homework5.apiary-mock.com/api/login"
    }

    override suspend fun getLoginData(
        s: String,
        s1: String,
        paramObject: JSONObject,

        ): Flow<Any> = flow {
        val response = api.getLoginDataAsync(URL_GET_PUBLIC_IP,paramObject.toString()).await()
        val headerValue= response.headers()["X-Acc"]

        var hashMap : HashMap<String, String>
                = HashMap()

        var userId = response.body()?.data?.userId.toString()
        var userName = response.body()?.data?.userName.toString()
        var isDeleted = response.body()?.data?.isDeleted.toString()

        hashMap.put("userId" , response.body()?.data?.userId.toString())
        hashMap.put("userName" , response.body()?.data?.userName.toString())
        hashMap.put("isDeleted" , response.body()?.data?.isDeleted.toString())
        if (headerValue != null) {
            hashMap.put("headerValue" , headerValue)
        }

        val result= loginDao.insertLogin(headerValue?.let {
            Login(
                0,
                userId,
                userName,
                isDeleted,
                it
            )
        })

        emit(hashMap)

    }.flowOn(Dispatchers.IO)

}