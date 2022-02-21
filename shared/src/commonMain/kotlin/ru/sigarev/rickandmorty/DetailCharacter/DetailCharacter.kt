package ru.sigarev.rickandmorty.DetailCharacter

import com.arkivanov.decompose.value.Value
import ru.sigarev.data_remote.model.Character
import ru.sigarev.rickandmorty.domain.CharacterDomain

interface DetailCharacter {
    val model: Value<Model>

    fun openEpisodes()

    data class Model(
        val character: CharacterDomain
    )
}