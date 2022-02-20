package ru.sigarev.rickandmorty.CharacterList.Store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sigarev.data_remote.characters.CharactersRepository
import ru.sigarev.data_remote.model.Character
import ru.sigarev.rickandmorty.CharacterList.Store.CharacterListStore.Intent
import ru.sigarev.rickandmorty.CharacterList.Store.CharacterListStore.State

internal class CharacterListStoreProvider(
    private val storeFactory: StoreFactory,
    private val charactersRepository: CharactersRepository
) {
    fun provide(): CharacterListStore = object : CharacterListStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = CharacterListStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    private sealed class Result {
        data class CharacterLoaded(val characters: List<Character>) : Result()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Nothing>() {

        // Action: Called once by the bootstrapper
        override fun executeAction(action: Unit, getState: () -> State) {
            fetchCharacters()
        }

        // Intent: Called via a user interaction
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.FetchPageCharacters -> fetchCharacters()
            }
        }

        // Fetch quote via a suspend function and dispatch the result to the reducer.
        private fun fetchCharacters() {
            scope.launch {
                val newQuote = withContext(Dispatchers.Default) {
                    charactersRepository.getCharacters(0)
                }
                dispatch(Result.CharacterLoaded(newQuote))
            }
        }
    }


    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State {
            return when (result) {
                is Result.CharacterLoaded -> copy(
                    numberPage = numberPage + 1,
                    characters = result.characters
                )
            }
        }
    }
}