package ru.sigarev.rickandmorty.android.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import ru.sigarev.rickandmorty.EpisodeList.EpisodeList

@Composable
fun EpisodesScreen(
    episodeList: EpisodeList,
    modifier: Modifier = Modifier
) {
    val state = episodeList.model.subscribeAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsWithImePadding()
    ) {
        LazyColumn {
            items(state.value.episodes) {
                Text(it.name)
            }
        }
    }
}