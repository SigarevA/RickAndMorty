package ru.sigarev.rickandmorty.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
class EpisodeDomain(
    val id: Long,
    val name: String,
    val airDate: String,
) : Parcelable