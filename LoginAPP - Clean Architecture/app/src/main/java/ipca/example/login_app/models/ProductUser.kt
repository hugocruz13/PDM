package ipca.example.login_app.models

import java.util.Date

data class ProductUser(
    var title: String? = null,
    var price: Double? = null,
    var image: String? = null,
    var data : Date? = null,
    var quantidade: Int? = null,
)
