package com.example.fetchlistwithretrofit.data

import com.example.fetchlistwithretrofit.data.model.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class ProductsRepositoryImpl(private val apiService: ApiService):ProductsRepository{
    override suspend fun getProductsList(): Flow<Result<List<Product>>> {
        return flow {
            val productsFromApi = try{
                apiService.getProductsList()

            }catch(e:IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow

            }catch (e:HttpException){
                e.printStackTrace()
                emit(Result.Error(message = e.message))
                return@flow

            }catch (e:Exception){
                e.printStackTrace()
                emit(Result.Error(message = e.message))
                return@flow

            }

            emit(Result.Success(data = productsFromApi.products))
        }
    }
}