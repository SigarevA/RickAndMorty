package ru.sigarev.data_remote.characters

import ru.sigarev.data_remote.model.Character
import ru.sigarev.data_remote.model.ResponseApi

interface CharactersRepository {
    suspend fun getCharacters(numberPage: Int): List<Character>
}