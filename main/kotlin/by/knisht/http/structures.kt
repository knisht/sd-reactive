package by.knisht.http

data class Request(val method : Method, val path : List<String>, val parameters: Map<String, String>)

enum class Method {
    GET, POST
}

data class Response(val code : Int, val content : String?)