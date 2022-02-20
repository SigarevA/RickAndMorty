package ru.sigarev.rickandmorty.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.coil.CoilImage
import ru.sigarev.rickandmorty.DetailCharacter.DetailCharacter

@Composable
fun DetailCharacterScreen(
    detailCharacterComponent: DetailCharacter,
    modifier: Modifier = Modifier
) {
    val state = detailCharacterComponent.model.subscribeAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsWithImePadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            CoilImage(
                imageModel = state.value.character.image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(124.dp)
                    .clip(RoundedCornerShape(64.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.value.character.name)
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.value.character.location.name)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${state.value.character.type} , ${state.value.character.status}")
        }
    }
}