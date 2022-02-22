package ru.sigarev.rickandmorty.android.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.coil.CoilImage
import ru.sigarev.rickandmorty.detail_character.DetailCharacter
import ru.sigarev.rickandmorty.android.R

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
            Text(
                state.value.character.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${state.value.character.type} , ${state.value.character.status}",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color(0xFFF6F6F6),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.content),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(painter = painterResource(R.drawable.ic_location), contentDescription = null)
                Text(
                    state.value.character.location.name,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        detailCharacterComponent.openEpisodes()
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(painter = painterResource(R.drawable.ic_movies), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(id = R.string.action_episodes),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    contentDescription = null
                )
            }
        }
    }
}