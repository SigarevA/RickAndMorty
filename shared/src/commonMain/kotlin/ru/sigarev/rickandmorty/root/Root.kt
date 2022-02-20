package ru.sigarev.rickandmorty.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.CharacterList.CharacterList

interface Root {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class Main(val component: CharacterList) : Child()
    }
}