package ru.sigarev.data_remote.model

import kotlinx.serialization.SerialName

class Episode(
    val id : Long,
    val name : String,
    @SerialName("air_date")
    val airDate : String,
    val episode : String,
    val characters : List<String>,
    val url : String,
    val created : String
)