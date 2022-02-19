package ru.sigarev.data_remote.characters

import io.ktor.client.*
import io.ktor.client.request.*
import ru.sigarev.data_remote.model.Character

internal class CharactersRepositoryImpl(private val client: HttpClient) : CharactersRepository {
    override suspend fun getCharacters(numberPage: Int): List<Character> =
        client.get<List<Character>> {
            parameter("page", numberPage)
        }
}