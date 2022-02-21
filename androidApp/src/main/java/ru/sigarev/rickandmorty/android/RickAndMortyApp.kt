package ru.sigarev.rickandmorty.android

import android.app.Application
import org.kodein.di.DI
import ru.sigarev.rickandmorty.android.di.DIHolder
import ru.sigarev.rickandmorty.di.componentModule

class RickAndMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DIHolder.di = DI {
            import(componentModule)
        }
    }
}