package com.joyce.book_finder.di

import com.joyce.book_finder.ui.login.LoginViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.mercadoeditorial.org/"

val module = module {
    viewModel {LoginViewModel() }

    //single { retrofit.create(RetrofitService::class.java) }
}

val logging =  HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

private val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()


