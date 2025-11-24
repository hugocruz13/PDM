package ipca.example.apicaller.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Product(

    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var urlToImage: String? = null,
    var price: Double? = null
) {
    companion object {
        fun fromJson(json: JSONObject): Product {
            return Product(
                id = json.optInt("id", 0),
                title = json.optString("title", null),
                description = json.optString("description", null),
                urlToImage = json.optJSONArray("images")?.optString(0),
                price = json.optDouble("price", 0.0)
            )
        }
    }
}

fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}

