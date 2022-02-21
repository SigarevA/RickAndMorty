package ru.sigarev.data_remote.responses

import kotlinx.serialization.Serializable
import ru.sigarev.data_remote.model.Character

@Serializable
class CharacterResponse(
    val info: InfoResponse,
    val results: List<Character>
)