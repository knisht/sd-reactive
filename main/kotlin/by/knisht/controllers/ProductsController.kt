package by.knisht.controllers

import by.knisht.database.Product
import by.knisht.database.User
import by.knisht.http.Method
import by.knisht.http.Request
import by.knisht.http.Response
import by.knisht.reflect.PathController
import rx.Observable

@Suppress("unused")
class ProductsController : PathController {
    override fun applicableTo(request: Request): Boolean = request.path.singleOrNull() == "products"

    override fun processRequest(request: Request): Observable<Response> {
        return when (request.method) {
            Method.GET -> {
                val userId = request.parameters["id"]?.toInt() ?: return signalWrongRequest()
                User.findById(userId).flatMap { user ->
                    Product.getAll().map {
                        it.copy(
                            price = PriceConverter.convertPrices(
                                User.Companion.Currency.DOLLAR,
                                user.currency,
                                it.price
                            )
                        )
                    }
                }.collect({ ArrayList() }, { list: ArrayList<Product>, el -> list.add(el) })
                    .map { Response(200, it.toString()) }
            }
            Method.POST -> {
                val id = request.parameters["id"]?.toInt() ?: return signalWrongRequest()
                val name = request.parameters["name"] ?: return signalWrongRequest()
                val price = request.parameters["price"]?.toDouble() ?: return signalWrongRequest()
                val product = Product(id, name, price)
                Product.persist(product).compose { Observable.just(Response(200, "OK")) }
            }
        }
    }
}