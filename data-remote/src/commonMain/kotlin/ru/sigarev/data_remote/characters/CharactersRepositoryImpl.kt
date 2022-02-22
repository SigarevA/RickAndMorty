package ru.sigarev.data_remote.characters

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import ru.sigarev.data_remote.model.Character
import ru.sigarev.data_remote.model.ResponseApi
import ru.sigarev.data_remote.responses.CharacterResponse

internal class CharactersRepositoryImpl(private val client: HttpClient) : CharactersRepository {
    override suspend fun getCharacters(numberPage: Int): ResponseApi<List<Character>> =
        try {
            val data = client.get<CharacterResponse>("https://rickandmortyapi.com/api/character") {
                parameter("page", numberPage)
            }.results
            ResponseApi(200, data, null)
        } catch (ex: ClientRequestException) {
            ResponseApi(ex.response.status.value, null, null)
        } catch (ex: Exception) {
            ResponseApi(-1, null, ex)
        }
}