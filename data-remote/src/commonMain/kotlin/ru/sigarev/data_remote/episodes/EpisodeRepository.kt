package ru.sigarev.data_remote.episodes

import ru.sigarev.data_remote.model.Episode
import ru.sigarev.data_remote.model.ResponseApi

interface EpisodeRepository {
    suspend fun getSingleEpisodes(episode: String): ResponseApi<Episode>
    suspend fun getMultiEpisodes(episodes: String): ResponseApi<List<Episode>>
}