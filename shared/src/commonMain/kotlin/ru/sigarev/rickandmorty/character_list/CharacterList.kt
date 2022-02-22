package ru.sigarev.rickandmorty.character_list

import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.domain.CharacterDomain

interface CharacterList {
    val model: Value<Model>

    fun loadPage()
    fun openDetail(characterDomain: CharacterDomain)
    fun removeException()
    fun refresh()

    data class Model(
        val characters: List<CharacterDomain> = emptyList(),
        val isLoadPage: Boolean = false,
        val isInitLoading: Boolean = false,
        val isFull: Boolean = false,
        val throwable: Throwable? = null
    )
}