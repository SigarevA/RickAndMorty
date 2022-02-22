package ru.sigarev.data_remote.episodes

import io.ktor.client.*
import io.ktor.client.request.*
import ru.sigarev.data_remote.model.Episode
import ru.sigarev.data_remote.model.ResponseApi

internal class EpisodeRepositoryImpl(private val client: HttpClient) : EpisodeRepository {
    override suspend fun getSingleEpisodes(episode: String): ResponseApi<Episode> {
        return try {
            val data = client.get<Episode>("$PATH$episode")
            ResponseApi(200, data, null)
        } catch (ex: Exception) {
            ResponseApi(-1, null, ex)
        }
    }

    override suspend fun getMultiEpisodes(episodes: String): ResponseApi<List<Episode>> {
        return try {
            val data =
                client.get<List<Episode>>("$PATH$episodes")
            ResponseApi(200, data, null)
        } catch (ex: Exception) {
            ResponseApi(-1, null, ex)
        }
    }

    companion object {
        private const val PATH = "https://rickandmortyapi.com/api/episode/"
    }
}