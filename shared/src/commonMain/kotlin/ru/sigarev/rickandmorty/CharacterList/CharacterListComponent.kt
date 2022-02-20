package ru.sigarev.rickandmorty.CharacterList

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.sigarev.data_remote.characters.CharactersRepository
import ru.sigarev.rickandmorty.CharacterList.CharacterList.Model
import ru.sigarev.rickandmorty.CharacterList.Store.CharacterListStoreProvider
import ru.sigarev.rickandmorty.utils.asValue

internal class CharacterListComponent(
    componentContext: ComponentContext,
    charactersRepository: CharactersRepository
) : CharacterList, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        CharacterListStoreProvider(
            DefaultStoreFactory(),
            charactersRepository
        ).provide()
    }

    override val model: Value<Model> = store.asValue().map {
        Model(it.characters)
    }
}