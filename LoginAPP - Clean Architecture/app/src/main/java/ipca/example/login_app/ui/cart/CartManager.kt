package ipca.example.login_app.ui.cart

import androidx.compose.runtime.mutableStateListOf
import ipca.example.login_app.models.CartItem
import ipca.example.login_app.models.Product
import ipca.example.login_app.room.CartItemDao
import ipca.example.login_app.room.CartItemEntity
import kotlin.IllegalArgumentException

fun Product.toEntity(quantity: Int): CartItemEntity {
    return CartItemEntity(
        productId = this.id ?: throw IllegalArgumentException("Product ID cannot be null"),
        title = this.title,
        price = this.price,
        image = this.image,
        quantity = quantity
    )
}

fun CartItemEntity.toCartItem(): CartItem {
    val product = Product(
        id = this.productId,
        title = this.title,
        price = this.price,
        image = this.image,
        quantity = null
    )
    return CartItem(
        product = product,
        initialQuantity = this.quantity
    )
}


class CartManager(private val cartItemDao: CartItemDao) {

    val items = mutableStateListOf<CartItem>()

    suspend fun loadCartItems() {
        items.clear()
        val entities = cartItemDao.getAll()
        items.addAll(entities.map { it.toCartItem() })
    }

    suspend fun addProduct(product: Product) {
        val existingItem = cartItemDao.getAll().find { it.productId == product.id }

        if (existingItem != null) {
            val newQuantity = existingItem.quantity + 1
            val updatedEntity = existingItem.copy(quantity = newQuantity)
            cartItemDao.insert(updatedEntity)
        } else {
            val newEntity = product.toEntity(quantity = 1)
            cartItemDao.insert(newEntity)
        }
        loadCartItems()
    }

    suspend fun removeUnit(product: Product) {
        val existingItem = cartItemDao.getAll().find { it.productId == product.id }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                val newQuantity = existingItem.quantity - 1
                val updatedEntity = existingItem.copy(quantity = newQuantity)
                cartItemDao.insert(updatedEntity)
            } else {
                cartItemDao.delete(existingItem)
            }
        }
        loadCartItems()
    }

    suspend fun removeItem(product: Product) {
        val existingItem = cartItemDao.getAll().find { it.productId == product.id }
        if (existingItem != null) {
            cartItemDao.delete(existingItem)
        }
        loadCartItems()
    }

    suspend fun clearCart() {
        cartItemDao.clear()
        loadCartItems()
    }

    fun total(): Double {
        var total = 0.0
        items.forEach { item ->
            val price = item.product?.price ?: 0.0
            total += price * item.quantity
        }
        return total
    }
}