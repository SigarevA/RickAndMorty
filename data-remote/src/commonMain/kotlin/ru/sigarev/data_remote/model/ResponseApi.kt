package ru.sigarev.data_remote.model

class ResponseApi<T> (
    val statusCode: Int,
    val data: T?,
    val exception: Throwable?
)