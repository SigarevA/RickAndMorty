package ru.sigarev.rickandmorty.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.kodein.di.DI
import org.kodein.di.factory
import ru.sigarev.rickandmorty.character_list.CharacterList
import ru.sigarev.rickandmorty.detail_character.DetailCharacterComponent
import ru.sigarev.rickandmorty.episode_list.EpisodeList
import ru.sigarev.rickandmorty.di.CharacterListArguments
import ru.sigarev.rickandmorty.di.EpisodeListArguments
import ru.sigarev.rickandmorty.domain.CharacterDomain

internal class RootComponent(
    di: DI,
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {
    private val characterListFactory by di.factory<CharacterListArguments, CharacterList>()
    private val episodeListFactory by di.factory<EpisodeListArguments, EpisodeList>()

    private val router =
        router<Config, Root.Child>(
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, Root.Child>> =
        router.state

    private fun createChild(config: Config, componentContext: ComponentContext): Root.Child =
        when (config) {
            is Config.Main -> Root.Child.Main(
                characterListFactory(CharacterListArguments(
                    componentContext
                ) { character ->
                    if (routerState.value.activeChild.instance is Root.Child.Main)
                        router.push(Config.DetailCharacter(character))
                })
            )
            is Config.DetailCharacter -> Root.Child.Detail(
                DetailCharacterComponent(config.character) { episodes ->
                    if (routerState.value.activeChild.instance is Root.Child.Detail)
                        router.push(Config.EpisodesList(episodes))
                }
            )
            is Config.EpisodesList -> Root.Child.Episodes(
                episodeListFactory(
                    EpisodeListArguments(
                        componentContext,
                        config.episodes
                    )
                )
            )
        }

    private sealed class Config : Parcelable {
        @Parcelize
        object Main : Config()

        @Parcelize
        class DetailCharacter(val character: CharacterDomain) : Config()

        @Parcelize
        class EpisodesList(val episodes: List<String>) : Config()
    }
}