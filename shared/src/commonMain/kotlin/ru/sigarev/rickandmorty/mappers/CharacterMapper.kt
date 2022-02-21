package ru.sigarev.rickandmorty.mappers

import ru.sigarev.data_remote.model.Character
import ru.sigarev.rickandmorty.domain.CharacterDomain

fun Character.toCharacterDomain() =
    CharacterDomain(
        this.id,
        this.name,
        this.status,
        this.species,
        this.type,
        this.gender,
        this.origin.toCharacterLocationDomain(),
        this.location.toCharacterLocationDomain(),
        this.image,
        this.episode,
        this.url,
        this.created
    )