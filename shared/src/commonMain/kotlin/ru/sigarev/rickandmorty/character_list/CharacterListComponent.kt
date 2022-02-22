package ru.sigarev.rickandmorty.character_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.sigarev.data_remote.characters.CharactersRepository
import ru.sigarev.rickandmorty.character_list.CharacterList.Model
import ru.sigarev.rickandmorty.character_list.store.CharacterListStore
import ru.sigarev.rickandmorty.character_list.store.CharacterListStoreProvider
import ru.sigarev.rickandmorty.domain.CharacterDomain
import ru.sigarev.rickandmorty.utils.asValue

internal class CharacterListComponent(
    componentContext: ComponentContext,
    charactersRepository: CharactersRepository,
    val navigateToDetail: (CharacterDomain) -> Unit
) : CharacterList, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        CharacterListStoreProvider(
            DefaultStoreFactory(),
            charactersRepository
        ).provide()
    }

    override val model: Value<Model> = store.asValue().map {
        Model(
            it.characters,
            it.isPageLoading,
            it.isInitLoading,
            it.isFull,
            it.throwable
        )
    }

    override fun loadPage() {
        store.accept(CharacterListStore.Intent.FetchPageCharacters)
    }

    override fun openDetail(character: CharacterDomain) {
        navigateToDetail(character)
    }

    override fun removeException() {
        store.accept(CharacterListStore.Intent.RemoveError)
    }

    override fun refresh() {
        store.accept(CharacterListStore.Intent.RefreshCharacters)
    }
}