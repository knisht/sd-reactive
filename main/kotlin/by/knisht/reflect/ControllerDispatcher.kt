package by.knisht.reflect

import by.knisht.http.Request
import by.knisht.http.Response
import org.reflections.Reflections
import rx.Observable
import rx.Single

class ControllerDispatcher {
    companion object {
        private val reflections = Reflections("by.knisht.controllers")
        private val subtypes = reflections.getSubTypesOf(PathController::class.java)
        private val instances: List<PathController> = subtypes.map { it.newInstance() }
    }

    fun dispatch(req: Request): Observable<Response> {
        val applicableInstance =
            instances.singleOrNull { it.applicableTo(req) } ?: return Observable.just(Response(404, null))
        return try {
            applicableInstance.processRequest(req)
        } catch (e : Throwable) {
            Observable.just(Response(500, null))
        }
    }
}