package ru.sigarev.data_remote.episodes

import ru.sigarev.data_remote.model.Episode

interface EpisodeRepository {
    suspend fun getAllEpisodes(): List<Episode>
    suspend fun filterEpisodes(episodes: List<String>): List<Episode>
}