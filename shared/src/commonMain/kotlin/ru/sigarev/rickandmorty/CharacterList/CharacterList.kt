package ru.sigarev.rickandmorty.CharacterList

import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.domain.CharacterDomain

interface CharacterList {
    val model: Value<Model>

    fun loadPage()
    fun openDetail(characterDomain: CharacterDomain)

    data class Model(
        val characters: List<CharacterDomain> = emptyList(),
        val isLoadPage: Boolean = false,
        val isInitLoading: Boolean = false
    )
}