package ru.sigarev.rickandmorty.episode_list.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.sigarev.rickandmorty.episode_list.store.EpisodeListStore.Intent
import ru.sigarev.rickandmorty.episode_list.store.EpisodeListStore.State
import ru.sigarev.rickandmorty.domain.EpisodeDomain

interface EpisodeListStore : Store<Intent, State, Nothing> {
    data class State(
        val episodes: List<EpisodeDomain>? = null,
        val loading: Boolean = false,
        val ex: Throwable? = null
    )

    sealed class Intent {
        class FetchEpisodes(val urlEpisodes: List<String>) : Intent()
    }
}