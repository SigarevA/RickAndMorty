package ru.sigarev.data_remote.episodes

import ru.sigarev.data_remote.model.Episode

interface EpisodeRepository {
    suspend fun getSingleEpisodes(episode: String): Episode
    suspend fun getMultiEpisodes(episodes: String): List<Episode>
}