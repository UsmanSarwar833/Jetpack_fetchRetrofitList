package com.example.fetchlistwithretrofit.presentation
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchlistwithretrofit.data.ProductsRepository
import com.example.fetchlistwithretrofit.data.Result
import com.example.fetchlistwithretrofit.data.model.product.Product
import com.example.fetchlistwithretrofit.data.model.product.Products
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(private  val productsRepository : ProductsRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()


    private  val _showErrorToast = MutableSharedFlow<Boolean>()
    val showErrorToast = _showErrorToast.asSharedFlow()

    //    private val _showErrorToastChannel = Channel<Boolean>()
//    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    init {
        viewModelScope.launch {
            productsRepository.getProductsList().collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        _showErrorToast.emit(true)
                        //_showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                      //  _showErrorToast.emit(true)
                        result.data?.let { products ->
                            _products.update { products }
                        }
                    }
                }
            }
        }
    }
}

