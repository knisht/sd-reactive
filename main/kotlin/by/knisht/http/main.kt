package by.knisht.http

import by.knisht.reflect.ControllerDispatcher
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.http.HttpMethod
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServer
import io.reactivex.netty.protocol.http.server.HttpServerRequest

private val dispatcher: ControllerDispatcher = ControllerDispatcher()

fun main() {
    HttpServer.newServer(8000).start { req, resp ->
        try {
            val request = parseRequest(req)
            val result = dispatcher.dispatch(request)
            result.subscribe { resp.status = HttpResponseStatus.valueOf(it.code) }
            resp.writeString(result.map { it.content })
        } catch (e : IllegalStateException) {
            resp.setStatus(HttpResponseStatus.BAD_REQUEST)
        }
    }.awaitShutdown()
}

private fun parseRequest(request: HttpServerRequest<ByteBuf>): Request {
    val path = request.decodedPath.split("/").drop(1)
    val method = when(request.httpMethod) {
        HttpMethod.GET -> Method.GET
        HttpMethod.POST -> Method.POST
        else -> throw IllegalStateException()
    }
    val parameters = request.queryParameters.mapValues { it.value.single() }
    return Request(method, path, parameters)
}