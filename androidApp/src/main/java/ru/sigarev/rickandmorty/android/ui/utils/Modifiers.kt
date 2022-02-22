package ru.sigarev.rickandmorty.android.ui.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.customPlaceholder(rounded: Dp): Modifier =
    placeholder(
        visible = true,
        color = Color.Gray.copy(alpha = 0.3f),
        shape = RoundedCornerShape(rounded),
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = Color.White,
        )
    )