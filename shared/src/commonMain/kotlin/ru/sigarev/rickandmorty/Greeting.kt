package ru.sigarev.rickandmorty

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}