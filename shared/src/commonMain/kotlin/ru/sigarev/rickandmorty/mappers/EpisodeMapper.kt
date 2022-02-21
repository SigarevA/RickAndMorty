package ru.sigarev.rickandmorty.mappers

import ru.sigarev.data_remote.model.Episode
import ru.sigarev.rickandmorty.domain.EpisodeDomain

fun Episode.toEpisodeDomain(): EpisodeDomain =
    EpisodeDomain(
        this.id,
        this.name,
        this.airDate
    )