package ru.sigarev.rickandmorty.CharacterList.Store

import com.arkivanov.mvikotlin.core.store.Store
import ru.sigarev.data_remote.model.Character
import ru.sigarev.rickandmorty.CharacterList.Store.CharacterListStore.Intent
import ru.sigarev.rickandmorty.CharacterList.Store.CharacterListStore.State

interface CharacterListStore : Store<Intent, State, Nothing> {

    data class State(
        val numberPage: Int = 0,
        val characters: List<Character> = emptyList()
    )

    sealed class Intent {
        object FetchPageCharacters : Intent()
    }
}