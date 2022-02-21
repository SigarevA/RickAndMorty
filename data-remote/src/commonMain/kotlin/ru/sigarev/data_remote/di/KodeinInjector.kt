package ru.sigarev.data_remote.di

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.sigarev.data_remote.characters.CharactersRepository
import ru.sigarev.data_remote.characters.CharactersRepositoryImpl
import ru.sigarev.data_remote.episodes.EpisodeRepository
import ru.sigarev.data_remote.episodes.EpisodeRepositoryImpl

private val client by lazy {
    HttpClient() {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
}

val repositoryModule = DI.Module("RepositoryModule") {
    bindSingleton<CharactersRepository> { CharactersRepositoryImpl(client) }
    bindSingleton<EpisodeRepository> { EpisodeRepositoryImpl(client) }
}