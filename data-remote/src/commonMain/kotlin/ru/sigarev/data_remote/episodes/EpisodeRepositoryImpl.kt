package ru.sigarev.data_remote.episodes

import io.ktor.client.*
import io.ktor.client.request.*
import ru.sigarev.data_remote.model.Episode

internal class EpisodeRepositoryImpl(private val client: HttpClient) : EpisodeRepository {
    override suspend fun getAllEpisodes(): List<Episode> =
        client.get<List<Episode>>("")


    override suspend fun filterEpisodes(episodes: List<String>): List<Episode> {
        TODO("Not yet implemented")
    }
}