package ru.sigarev.rickandmorty.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class CharacterLocationDomain(
    val name: String,
    val url: String
) : Parcelable