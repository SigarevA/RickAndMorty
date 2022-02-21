package ru.sigarev.rickandmorty.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class CharacterDomain(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocationDomain,
    val location: CharacterLocationDomain,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) : Parcelable