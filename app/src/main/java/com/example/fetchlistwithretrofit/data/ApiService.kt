package com.example.fetchlistwithretrofit.data

import com.example.fetchlistwithretrofit.data.model.product.Products
import retrofit2.http.GET 

interface ApiService {


//   @GET("products/{type}")
//   suspend fun getProductsList(
//      @Path("type") type: String,
//      @Query("page") page:Int,
//      @Query("api_key") apiKey:String
//   ): Products

   @GET("products/")
   suspend fun getProductsList(): Products


    companion object {
      const val BASE_URL = "https://dummyjson.com/"
   }

}
