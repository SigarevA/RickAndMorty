package ru.sigarev.rickandmorty.di

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.instance
import ru.sigarev.data_remote.di.repositoryModule
import ru.sigarev.rickandmorty.CharacterList.CharacterList
import ru.sigarev.rickandmorty.CharacterList.CharacterListComponent
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
}

data class CharacterListArguments(
    val ctx: ComponentContext,
    val navigateToDetail: (CharacterDomain) -> Unit
)