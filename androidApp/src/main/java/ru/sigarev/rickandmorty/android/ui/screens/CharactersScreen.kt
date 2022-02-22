package ru.sigarev.rickandmorty.android.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.coil.CoilImage
import ru.sigarev.rickandmorty.character_list.CharacterList
import ru.sigarev.rickandmorty.android.R
import ru.sigarev.rickandmorty.android.ui.utils.customPlaceholder
import ru.sigarev.rickandmorty.domain.CharacterDomain

@Composable
fun CharactersScreen(
    characterList: CharacterList,
    modifier: Modifier = Modifier
) {
    val state = characterList.model.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
            .navigationBarsWithImePadding(),
        contentAlignment = Alignment.Center
    ) {
        if (state.value.isInitLoading) {
            Loading()
        } else {
            if (state.value.throwable != null && state.value.characters.isEmpty()) {
                LoadingError {
                    characterList.refresh()
                }
            } else {
                if (state.value.throwable != null) {
                    LaunchedEffect(true) {
                        snackbarHostState.showSnackbar("1")
                        characterList.removeException()
                    }
                }
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.value.characters) {
                        ItemCharacter(characterDomain = it) {
                            characterList.openDetail(it)
                        }
                    }
                    if (state.value.isLoadPage) {
                        item {
                            PageLoadingIndicator()
                        }
                    }
                }
            }
        }
        ErrorSnackBar(snackbarHostState)
    }
}

@Composable
fun PageLoadingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingError(refresh: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(id = R.string.loading_error),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(onClick = { refresh() }) {
            Text(stringResource(id = R.string.repeat))
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(64.dp)
                            .customPlaceholder(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .height(18.dp)
                            .customPlaceholder(9.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCharacter(
    characterDomain: CharacterDomain,
    openDetail: () -> Unit
) {
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
                    openDetail()
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(
                imageModel = characterDomain.image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                characterDomain.name,
                maxLines = 1
            )
        }
    }
}

@Composable
fun BoxScope.ErrorSnackBar(hostState: SnackbarHostState) {
    SnackbarHost(
        hostState = hostState,
        modifier = Modifier.align(Alignment.BottomCenter),
        snackbar = { data ->
            Surface(
                color = MaterialTheme.colors.error,
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.loading_error),
                    color = MaterialTheme.colors.onError,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}