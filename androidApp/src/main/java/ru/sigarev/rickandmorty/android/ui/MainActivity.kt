package ru.sigarev.rickandmorty.android.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.crossfade
import com.google.accompanist.insets.ProvideWindowInsets
import org.kodein.di.factory
import ru.sigarev.rickandmorty.android.di.DIHolder
import ru.sigarev.rickandmorty.android.ui.screens.CharactersScreen
import ru.sigarev.rickandmorty.android.ui.screens.DetailCharacterScreen
import ru.sigarev.rickandmorty.android.ui.theme.RickAndMortyAppTheme
import ru.sigarev.rickandmorty.root.Root

class MainActivity : AppCompatActivity() {

    private val root by DIHolder.di.factory<ComponentContext, Root>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureEdgeToEdge()
        val component = root(defaultComponentContext())

        setContent {
            RickAndMortyAppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Children(
                            routerState = component.routerState,
                            animation = crossfade()
                        ) {
                            when (val child = it.instance) {
                                is Root.Child.Main -> {
                                    CharactersScreen(child.component)
                                }
                                is Root.Child.Detail -> {
                                    DetailCharacterScreen(child.component)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun configureEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
