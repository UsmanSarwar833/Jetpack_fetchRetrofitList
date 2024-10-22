package com.example.fetchlistwithretrofit

import androidx.compose.ui.platform.LocalContext
import com.example.fetchlistwithretrofit.data.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //initializing an instance of retrofit

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
       level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val api:ApiService = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                         .baseUrl(ApiService.BASE_URL).client(client).build().create(ApiService::class.java)


}