package ru.sigarev.rickandmorty.character_list.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.sigarev.rickandmorty.character_list.store.CharacterListStore.Intent
import ru.sigarev.rickandmorty.character_list.store.CharacterListStore.State
import ru.sigarev.rickandmorty.domain.CharacterDomain

interface CharacterListStore : Store<Intent, State, Nothing> {

    data class State(
        val numberPage: Int = 1,
        val characters: List<CharacterDomain> = emptyList(),
        val isPageLoading: Boolean = false,
        val isInitLoading: Boolean = false,
        val isFull: Boolean = false,
        val throwable: Throwable? = null
    )

    sealed class Intent {
        object FetchPageCharacters : Intent()
        object RemoveError : Intent()
        object RefreshCharacters : Intent()
    }
}