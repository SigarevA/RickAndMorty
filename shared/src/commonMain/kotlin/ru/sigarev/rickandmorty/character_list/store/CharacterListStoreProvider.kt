package ru.sigarev.rickandmorty.character_list.store

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
import ru.sigarev.rickandmorty.character_list.store.CharacterListStore.Intent
import ru.sigarev.rickandmorty.character_list.store.CharacterListStore.State
import ru.sigarev.rickandmorty.mappers.toCharacterDomain

internal class CharacterListStoreProvider(
    private val storeFactory: StoreFactory,
    private val charactersRepository: CharactersRepository
) {
    fun provide(): CharacterListStore =
        object : CharacterListStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = CharacterListStore::class.simpleName,
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Result {
        object InitLoading : Result()
        object PageLoading : Result()
        object FullLoading : Result()
        object UnsetException : Result()
        object Refresh : Result()
        data class CharacterLoaded(val characters: List<Character>) : Result()
        class ExceptionLoading(val throwable: Throwable) : Result()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Nothing>() {
        // Action: Called once by the bootstrapper
        override fun executeAction(action: Unit, getState: () -> State) {
            dispatch(Result.InitLoading)
            fetchCharacters(getState().numberPage)
        }

        // Intent: Called via a user interaction
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.FetchPageCharacters -> {
                    if (
                        with(getState()) {
                            characters.isNotEmpty() && !isInitLoading && !isPageLoading && !isFull
                        }
                    ) {
                        dispatch(Result.PageLoading)
                        fetchCharacters(getState().numberPage)
                    }
                }
                is Intent.RemoveError -> dispatch(Result.UnsetException)
                is Intent.RefreshCharacters -> {
                    dispatch(Result.Refresh)
                    fetchCharacters(1)
                }
            }
        }

        // Fetch quote via a suspend function and dispatch the result to the reducer.
        private fun fetchCharacters(numberPage: Int) {
            scope.launch {
                val response = withContext(Dispatchers.Default) {
                    charactersRepository.getCharacters(numberPage)
                }
                if (response.statusCode == 200)
                    dispatch(Result.CharacterLoaded(response.data!!))
                if (response.statusCode == 404)
                    dispatch(Result.FullLoading)
                response.exception?.let {
                    dispatch(Result.ExceptionLoading(it))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State {
            return when (result) {
                is Result.CharacterLoaded -> copy(
                    numberPage = numberPage + 1,
                    characters = characters.plus(result.characters.map { it.toCharacterDomain() }),
                    isPageLoading = false,
                    isInitLoading = false,
                    throwable = null
                )
                Result.InitLoading -> copy(isInitLoading = true)
                Result.PageLoading -> copy(isPageLoading = true)
                Result.FullLoading -> copy(
                    isPageLoading = false,
                    isInitLoading = false,
                    throwable = null,
                    isFull = false
                )
                Result.UnsetException -> copy(throwable = null)
                Result.Refresh -> copy(
                    numberPage = 1,
                    characters = emptyList(),
                    isPageLoading = false,
                    isInitLoading = true,
                    throwable = null,
                    isFull = true
                )
                is Result.ExceptionLoading -> reduceException(result.throwable)
            }
        }

        private fun State.reduceException(throwable: Throwable): State =
            copy(
                numberPage = numberPage,
                characters = characters,
                isPageLoading = false,
                isInitLoading = false,
                throwable = throwable
            )
    }
}