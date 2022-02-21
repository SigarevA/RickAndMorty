package ru.sigarev.rickandmorty.DetailCharacter

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.sigarev.rickandmorty.domain.CharacterDomain

internal class DetailCharacterComponent(
    private val character: CharacterDomain,
    private val openEpisodes: (List<String>) -> Unit
) : DetailCharacter {
    override val model: Value<DetailCharacter.Model> =
        MutableValue(DetailCharacter.Model(character))

    override fun openEpisodes() {
        openEpisodes(character.episode)
    }
}