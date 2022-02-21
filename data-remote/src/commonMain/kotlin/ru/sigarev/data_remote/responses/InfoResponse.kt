package ru.sigarev.data_remote.responses

import kotlinx.serialization.Serializable

@Serializable
class InfoResponse(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)