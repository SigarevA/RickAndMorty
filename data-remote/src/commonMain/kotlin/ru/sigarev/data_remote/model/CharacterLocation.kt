package ru.sigarev.data_remote.model

import kotlinx.serialization.Serializable

@Serializable
class CharacterLocation(
    val name: String,
    val url: String
)