package ru.sigarev.rickandmorty.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.CharacterList.CharacterList
import ru.sigarev.rickandmorty.DetailCharacter.DetailCharacter

interface Root {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class Main(val component: CharacterList) : Child()
        class Detail(val component: DetailCharacter) : Child()
    }
}