package ru.sigarev.rickandmorty.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.crossfade
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.insets.ProvideWindowInsets
import org.kodein.di.factory
import ru.sigarev.rickandmorty.android.di.DIHolder
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
                    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
                        Text("dhdjas")
                        Children(
                            routerState = component.routerState,
                            animation = crossfade()
                        ) {
                            when (val child = it.instance) {
                                is Root.Child.Main -> {
                                    val state = child.component.model.subscribeAsState()

                                    LazyColumn {
                                        item { Text("Start") }
                                        items(state.value.characters) {
                                            Text("1")
                                        }
                                        item { Text("End") }
                                    }
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
