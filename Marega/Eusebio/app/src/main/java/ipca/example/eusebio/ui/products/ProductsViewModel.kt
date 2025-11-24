package ipca.example.eusebio.ui.products

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.example.eusebio.models.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ProductsListState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductsListViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductsListState())
        private set

    fun fetchProducts() {
        uiState.value = uiState.value.copy(isLoading = true)


        val request = Request.Builder()
            .url("http://10.10.0.7:8000/products") //url("http://10.0.2.2:8000/products")
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

                    val newsResult = response.body!!.string()
                    val jsonResult = JSONObject(newsResult)
                    val productsList = arrayListOf<Product>()
                    if (jsonResult.getString("status") == "ok") {
                        val productsJson = jsonResult.getJSONArray("products")
                        for (i in 0 until productsJson.length()) {
                            val articleJson = productsJson.getJSONObject(i)
                            val article = Product.fromJson(articleJson)
                            productsList.add(article)
                        }
                    }
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        products = productsList
                    )
                }
            }
        })
    }

}