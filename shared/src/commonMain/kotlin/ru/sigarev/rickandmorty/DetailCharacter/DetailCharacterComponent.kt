package ru.sigarev.rickandmorty.DetailCharacter

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.domain.CharacterDomain

internal class DetailCharacterComponent(
    private val character: CharacterDomain
) : DetailCharacter {
    override val model: Value<DetailCharacter.Model> =
        MutableValue(DetailCharacter.Model(character))
}