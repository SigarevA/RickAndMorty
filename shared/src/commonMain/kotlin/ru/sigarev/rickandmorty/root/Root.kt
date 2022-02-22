package ru.sigarev.rickandmorty.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.character_list.CharacterList
import ru.sigarev.rickandmorty.detail_character.DetailCharacter
import ru.sigarev.rickandmorty.episode_list.EpisodeList

interface Root {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class Main(val component: CharacterList) : Child()
        class Detail(val component: DetailCharacter) : Child()
        class Episodes(val component: EpisodeList) : Child()
    }
}