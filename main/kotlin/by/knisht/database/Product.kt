package by.knisht.database

import com.mongodb.rx.client.Success
import org.bson.Document
import rx.Observable

data class Product(val id: Int, val name: String, val price: Double) {
    companion object {
        private const val ID = "id"
        private const val NAME = "name"
        private const val PRICE = "price"
        private const val PRODUCTS = "products"

        fun persist(product: Product): Observable<Success> {
            val newProduct = Document(ID, product.id).append(NAME, product.name).append(PRICE, product.price)
            return database.getCollection(PRODUCTS).insertOne(newProduct).apply { subscribe() }
        }

        fun getAll(): Observable<Product> {
            return database.getCollection(PRODUCTS).find().toObservable()
                .map { Product(it[ID] as Int, it[NAME] as String, it[PRICE] as Double) }
        }
    }
}