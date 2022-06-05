package com.joyce.book_finder.network

import com.joyce.book_finder.models.ResponseGetBooks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/v1.2/book?")
    suspend fun getAllBooks(@Query("titulo") title: String): Response<ResponseGetBooks>
}