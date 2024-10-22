package com.example.fetchlistwithretrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.fetchlistwithretrofit.data.ProductsRepositoryImpl
import com.example.fetchlistwithretrofit.data.model.product.Product
import com.example.fetchlistwithretrofit.presentation.ProductsViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ProductsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.api)) as T
            }
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val productList = viewModel.products.collectAsState().value
            val context = LocalContext.current
            LaunchedEffect(key1 = viewModel.showErrorToast) {
                viewModel.showErrorToast.collectLatest { show ->
                    if(show){ Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show() } } }

            if(productList.isEmpty()){
                Box(modifier = Modifier.fillMaxSize(1f),){
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,  horizontalAlignment = Alignment.CenterHorizontally){ CircularProgressIndicator() }
//                    Column(verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text("No list found")
//                    }
                }
            }else {
                  LazyColumn(
                      modifier = Modifier
                          .fillMaxSize()
                          .padding(horizontal = 15.dp,).padding(top = 50.dp),
                      horizontalAlignment = Alignment.CenterHorizontally) {
                     items(productList.size){index ->

                        Product(product = productList[index],)

                     }
                  }
                }
            }

        }
    }




@Composable
 fun Product(product:Product,) {
    val imageState = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current).data(product.thumbnail).size(Size.ORIGINAL).build()).state

    Box(modifier = Modifier.fillMaxWidth().height(400.dp)){
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .height(380.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
        ) {
            if (imageState is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
            if (imageState is AsyncImagePainter.State.Success) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                        .background(color = Color(0xffd7e0ce)),
                    painter = imageState.painter,
                    contentDescription = product.title,
                    contentScale = ContentScale.FillWidth
                )
            }

            Text(
                "${product.title} -- Price:${product.price}$",
                modifier = Modifier.padding(top = 20.dp, bottom = 30.dp, start = 20.dp),
                fontSize = 20.sp
            )
            Text(product.description, modifier = Modifier.padding(start = 20.dp), fontSize = 14.sp)

        }
    }



 }

///ubl
/// Muhammad Usman Sarwar
///account number 12870081041847014


//3643
//6348



//flows are two types
//1)cold flow -> means simple flow
//2) hot flow -> hot flow have two types 1)shared flow 2)stateflow





//simple flow
//simple flow mai function us time tak data emit nahi karta jab tak koi component collect nahi kare ga ,means emit data ko collect karne ka method mojood na ho

//shared flow
//shared flow is a hot flow,shared flow data emit karta rahe ga no matter ke is ka collector hai ya nahi.share flow listen all the emitted values
// shared flow doesn't need initial value.
//shared flow share there emittedvalues with all the collectors



//state flow
//state flow is a hot flow,state flow maintain its latest state,means it saves last value in  state.state flow listen only last emitted value.
//state flow need initial value

