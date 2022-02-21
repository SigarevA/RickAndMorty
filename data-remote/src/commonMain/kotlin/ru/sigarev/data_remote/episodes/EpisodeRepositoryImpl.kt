package ru.sigarev.data_remote.episodes

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import ru.sigarev.data_remote.model.Episode

internal class EpisodeRepositoryImpl(private val client: HttpClient) : EpisodeRepository {
    override suspend fun getSingleEpisodes(episode: String): Episode {
        return client.get("https://rickandmortyapi.com/api/episode/$episode")
    }

    override suspend fun getMultiEpisodes(episodes: String): List<Episode> {
        return try {
            client.get("https://rickandmortyapi.com/api/episode/$episodes")
        } catch (ex: ClientRequestException) {
            emptyList()
        }
    }
}