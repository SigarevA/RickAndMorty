package ru.sigarev.rickandmorty.EpisodeList

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.sigarev.data_remote.episodes.EpisodeRepository
import ru.sigarev.rickandmorty.EpisodeList.store.EpisodeListStore
import ru.sigarev.rickandmorty.EpisodeList.store.EpisodeListStoreProvider
import ru.sigarev.rickandmorty.utils.asValue

internal class EpisodeListComponent(
    componentContext: ComponentContext,
    episodeRepository: EpisodeRepository,
    episodes: List<String>
) : EpisodeList, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        EpisodeListStoreProvider(
            DefaultStoreFactory(),
            episodeRepository
        ).provide()
    }

    init {
        store.accept(EpisodeListStore.Intent.FetchEpisodes(episodes))
    }

    override val model: Value<EpisodeList.Model> = store.asValue().map {
        EpisodeList.Model(
            it.episodes ?: emptyList(),
            it.loading,
            it.ex
        )
    }
}