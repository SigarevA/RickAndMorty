package ru.sigarev.rickandmorty.di

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.instance
import ru.sigarev.data_remote.di.repositoryModule
import ru.sigarev.rickandmorty.character_list.CharacterList
import ru.sigarev.rickandmorty.character_list.CharacterListComponent
import ru.sigarev.rickandmorty.episode_list.EpisodeList
import ru.sigarev.rickandmorty.episode_list.EpisodeListComponent
import ru.sigarev.rickandmorty.domain.CharacterDomain
import ru.sigarev.rickandmorty.root.Root
import ru.sigarev.rickandmorty.root.RootComponent

val componentModule = DI.Module("ComponentModule") {
    import(repositoryModule)

    bindFactory<ComponentContext, Root> { context ->
        RootComponent(di, context)
    }

    bindFactory<CharacterListArguments, CharacterList> { args ->
        CharacterListComponent(args.ctx, instance(), args.navigateToDetail)
    }

    bindFactory<EpisodeListArguments, EpisodeList> { args ->
        EpisodeListComponent(args.componentContext, instance(), args.episodes)
    }
}

data class CharacterListArguments(
    val ctx: ComponentContext,
    val navigateToDetail: (CharacterDomain) -> Unit
)

data class EpisodeListArguments(
    val componentContext: ComponentContext,
    val episodes: List<String>
)