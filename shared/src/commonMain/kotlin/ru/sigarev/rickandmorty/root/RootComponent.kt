package ru.sigarev.rickandmorty.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.kodein.di.DI
import org.kodein.di.factory
import ru.sigarev.rickandmorty.CharacterList.CharacterList

internal class RootComponent(
    di: DI,
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {
    private val characterListFactory by di.factory<ComponentContext, CharacterList>()

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
            is Config.Main -> Root.Child.Main(characterListFactory(componentContext))
        }

    private sealed class Config : Parcelable {
        @Parcelize
        object Main : Config()
    }
}