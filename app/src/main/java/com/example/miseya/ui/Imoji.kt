package com.example.miseya.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun Imoji(@DrawableRes drawableResId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(data = drawableResId),
        contentDescription = null,
        modifier = modifier
    )
}