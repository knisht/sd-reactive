package by.knisht.controllers

import by.knisht.database.User
import by.knisht.http.Method
import by.knisht.http.Request
import by.knisht.http.Response
import by.knisht.reflect.PathController
import rx.Observable

@Suppress("unused")
class UserController : PathController {
    override fun applicableTo(request: Request): Boolean = request.path.singleOrNull() == "users"

    override fun processRequest(request: Request): Observable<Response> {
        return when (request.method) {
            Method.GET -> User
                .getAll()
                .collect({ ArrayList() }, { list: ArrayList<User>, el -> list.add(el) })
                .map { Response(200, it.toString()) }
            Method.POST -> {
                val id = request.parameters["id"]?.toInt() ?: return signalWrongRequest()
                val name = request.parameters["name"] ?: return signalWrongRequest()
                val currency = request.parameters["currency"]?.toUpperCase()
                    ?.let { User.Companion.Currency.valueOf(it) } ?: return signalWrongRequest()
                val user = User(id, name, currency)
                User.persist(user).compose { Observable.just(Response(200, "OK")) }
            }
        }
    }
}