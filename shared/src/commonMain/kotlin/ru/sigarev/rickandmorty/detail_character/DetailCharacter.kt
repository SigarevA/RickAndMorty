package ru.sigarev.rickandmorty.detail_character

import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.domain.CharacterDomain

interface DetailCharacter {
    val model: Value<Model>

    fun openEpisodes()

    data class Model(
        val character: CharacterDomain
    )
}