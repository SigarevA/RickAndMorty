package ru.sigarev.data_remote.characters

import io.ktor.client.*
import io.ktor.client.request.*
import ru.sigarev.data_remote.model.Character
import ru.sigarev.data_remote.responses.CharacterResponse

internal class CharactersRepositoryImpl(private val client: HttpClient) : CharactersRepository {
    override suspend fun getCharacters(numberPage: Int): List<Character> =
        client.get<CharacterResponse>("https://rickandmortyapi.com/api/character/?page=1") {
            parameter("page", numberPage)
        }.results
}