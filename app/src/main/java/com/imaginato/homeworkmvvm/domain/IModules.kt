package com.imaginato.homeworkmvvm.domain

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imaginato.homeworkmvvm.data.local.login.LoginDatabase
import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import com.imaginato.homeworkmvvm.data.remote.login.LoginApi
import com.imaginato.homeworkmvvm.data.remote.login.LoginDataRepository
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.ui.login.LoginActivityViewModel

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://ifconfig.me/"

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val netModules = module {
    single { provideInterceptors() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideGson() }
}

val apiModules = module {
    single { provideDemoApi(get()) }
}

val repositoryModules = module {
    single { provideDemoRepo(get(), androidApplication(), get()) }
}

val viewModelModules = module {
    viewModel {
        LoginActivityViewModel()
    }

}

fun provideDemoRepo(api: LoginApi, context: Context, dao : LoginDao): LoginRepository {
    return LoginDataRepository(api,context,dao)
}

private fun provideDemoApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

fun provideDatabase(application: Application): LoginDatabase {

    return Room.databaseBuilder(application, LoginDatabase::class.java, "I-Database1")
        .fallbackToDestructiveMigration()
        .build()

}


fun provideDao(database: LoginDatabase): LoginDao {
    return database.loginDao
}
private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.readTimeout(2, TimeUnit.MINUTES)
    clientBuilder.connectTimeout(2, TimeUnit.MINUTES)
    interceptors.forEach { clientBuilder.addInterceptor(it) }
    return clientBuilder.build()
}

private fun provideInterceptors(): ArrayList<Interceptor> {
    val interceptors = arrayListOf<Interceptor>()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    interceptors.add(loggingInterceptor)
    return interceptors
}

fun provideGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}
