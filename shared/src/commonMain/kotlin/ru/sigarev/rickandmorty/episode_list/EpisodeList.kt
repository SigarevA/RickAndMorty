package ru.sigarev.rickandmorty.episode_list

import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.domain.EpisodeDomain

interface EpisodeList {
    val model: Value<Model>

    data class Model(
        val episodes: List<EpisodeDomain>,
        val loading: Boolean = false,
        val ex: Throwable? = null
    )
}