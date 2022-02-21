package ru.sigarev.rickandmorty.CharacterList.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.sigarev.rickandmorty.CharacterList.store.CharacterListStore.Intent
import ru.sigarev.rickandmorty.CharacterList.store.CharacterListStore.State
import ru.sigarev.rickandmorty.domain.CharacterDomain

interface CharacterListStore : Store<Intent, State, Nothing> {

    data class State(
        val numberPage: Int = 1,
        val characters: List<CharacterDomain> = emptyList(),
        val isPageLoading: Boolean = false,
        val isInitLoading: Boolean = false
    )

    sealed class Intent {
        object FetchPageCharacters : Intent()
    }
}