package ru.sigarev.rickandmorty.mappers

import ru.sigarev.data_remote.model.CharacterLocation
import ru.sigarev.rickandmorty.domain.CharacterLocationDomain

fun CharacterLocation.toCharacterLocationDomain() =
    CharacterLocationDomain(
        this.name,
        this.url
    )