package ru.sigarev.rickandmorty.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            .navigationBarsWithImePadding(),
        contentAlignment = Alignment.Center
    ) {
        if (state.value.loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        "Эпизоды",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                itemsIndexed(state.value.episodes) { i, episode ->
                    Column {
                        Surface(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    episode.name,
                                    style = MaterialTheme.typography.subtitle2,
                                    color = MaterialTheme.colors.onBackground
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    episode.airDate,
                                    style = MaterialTheme.typography.body2,
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.85f)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                    if (i != state.value.episodes.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}