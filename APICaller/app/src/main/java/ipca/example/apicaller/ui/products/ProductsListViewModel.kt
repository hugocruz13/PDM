package ipca.example.apicaller.ui.products

import android.R
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.example.apicaller.models.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ProductsList(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductsListViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductsList())
        private set

    fun fetchProducts(category:  String) {
        uiState.value = uiState.value.copy(isLoading = true)
        val request = Request.Builder()
            .url("https://dummyjson.com/products/category/${category}")
            .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Unexpected code $response"
                        )
                    }

                    val productsResult = response.body!!.string()
                    val jsonResult = JSONObject(productsResult)
                    val productList = arrayListOf<Product>()

                    val productsJson = jsonResult.getJSONArray("products")
                    for (i in 0 until productsJson.length()) {
                        val productJson = productsJson.getJSONObject(i)
                        val product = Product.fromJson(productJson)
                        productList.add(product)
                    }
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        products = productList
                    )
                }
            }
        })
    }

}
