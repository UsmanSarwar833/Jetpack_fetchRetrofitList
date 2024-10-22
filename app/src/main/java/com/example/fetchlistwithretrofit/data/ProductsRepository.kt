package com.example.fetchlistwithretrofit.data

import com.example.fetchlistwithretrofit.data.model.product.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsList():Flow<Result<List<Product>>>
}