package ru.sigarev.rickandmorty.episode_list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sigarev.data_remote.episodes.EpisodeRepository
import ru.sigarev.data_remote.model.Episode
import ru.sigarev.data_remote.model.ResponseApi
import ru.sigarev.rickandmorty.episode_list.store.EpisodeListStore.Intent
import ru.sigarev.rickandmorty.episode_list.store.EpisodeListStore.State
import ru.sigarev.rickandmorty.mappers.toEpisodeDomain

class EpisodeListStoreProvider(
    private val storeFactory: StoreFactory,
    private val episodeRepository: EpisodeRepository
) {

    fun provide(): EpisodeListStore =
        object : EpisodeListStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = EpisodeListStore::class.simpleName,
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}


    private sealed class Result {
        object Loading : Result()
        data class EpisodesLoaded(val episodes: List<Episode>) : Result()
        data class ExceptionLoading(val throwable: Throwable) : Result()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {}

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.FetchEpisodes -> {
                    dispatch(Result.Loading)
                    fetchEpisodes(intent.urlEpisodes)
                }
            }
        }

        private fun fetchEpisodes(urlEpisodes: List<String>) {
            scope.launch {
                val episodes = urlEpisodes.foldIndexed("", { i, acc, s ->
                    acc + s.split("/")
                        .let { it[it.lastIndex] } + if (i != urlEpisodes.lastIndex) "," else ""
                })
                if (urlEpisodes.size == 1) {
                    val response = withContext(Dispatchers.Default) {
                        episodeRepository.getSingleEpisodes(episodes)
                    }
                    response.notifyException()
                    response.data?.let {
                        dispatch(Result.EpisodesLoaded(listOf(it)))
                    }
                } else {
                    val response = withContext(Dispatchers.Default) {
                        episodeRepository.getMultiEpisodes(episodes)
                    }
                    response.notifyException()
                    response.data?.let {
                        dispatch(Result.EpisodesLoaded(it))
                    }
                }
            }
        }

        private fun ResponseApi<*>.notifyException() {
            exception?.let {
                dispatch(Result.ExceptionLoading(it))
            }
        }
    }

    private object ReducerImpl :
        Reducer<State, Result> {
        override fun State.reduce(result: Result): State {
            return when (result) {
                is Result.Loading -> copy(loading = true)
                is Result.EpisodesLoaded -> copy(
                    loading = false,
                    episodes = result.episodes.map { it.toEpisodeDomain() },
                    ex = null
                )
                is Result.ExceptionLoading -> copy(
                    loading = false,
                    episodes = null,
                    ex = result.throwable
                )
            }
        }
    }
}