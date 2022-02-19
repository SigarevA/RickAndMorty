package ru.sigarev.data_remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}

val client = HttpClient()

val response: HttpResponse   = client.get("https://ktor.io/")