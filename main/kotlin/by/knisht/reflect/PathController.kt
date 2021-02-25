package by.knisht.reflect

import by.knisht.http.Request
import by.knisht.http.Response
import rx.Observable

interface PathController {
    fun applicableTo(request: Request): Boolean

    fun processRequest(request: Request): Observable<Response>

    fun signalWrongRequest(): Observable<Response> = Observable.just(Response(400, null))
}