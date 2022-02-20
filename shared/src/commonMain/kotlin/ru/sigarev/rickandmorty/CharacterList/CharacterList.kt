package ru.sigarev.rickandmorty.CharacterList

import com.arkivanov.decompose.value.Value
import ru.sigarev.data_remote.model.Character

interface CharacterList {
    val model: Value<Model>

    data class Model(
        val characters: List<Character> = emptyList()
    )
}