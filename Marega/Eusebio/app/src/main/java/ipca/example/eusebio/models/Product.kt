package ipca.example.eusebio.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Product(
    var id: String? = null,
    var name: String? = null,
    var price: String? = null,
    var description: String? = null,
    var image_url: String? = null
) {
    companion object {
        fun fromJson(json: JSONObject): Product {
            return Product(
                json.getString("id"),
                json.getString("name"),
                json.getString("price"),
                json.getString("description"),
                json.getString("image_url")
            )
        }
    }
}


fun String.encodeUrl(): String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl(): String {
    return URLDecoder.decode(this, "UTF-8")
}
