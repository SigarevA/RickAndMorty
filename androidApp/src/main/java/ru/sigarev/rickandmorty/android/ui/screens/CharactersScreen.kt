package ru.sigarev.rickandmorty.android.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.coil.CoilImage
import ru.sigarev.rickandmorty.CharacterList.CharacterList

@Composable
fun CharactersScreen(
    characterList: CharacterList,
    modifier: Modifier = Modifier
) {
    val state = characterList.model.subscribeAsState()
    val scrollState = rememberLazyListState()
    val indexLastElements =
        remember(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset) {
            with(scrollState) { firstVisibleItemIndex + layoutInfo.visibleItemsInfo.lastIndex }
        }

    SideEffect {
        if (indexLastElements == state.value.characters.size - 1)
            characterList.loadPage()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsWithImePadding()
    ) {
        LazyColumn(
            state = scrollState
        ) {
            items(state.value.characters) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                characterList.openDetail(it)
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CoilImage(
                            imageModel = it.image,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(32.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            it.name,
                            maxLines = 1
                        )
                    }
                }
            }
            if (state.value.isLoadPage) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}