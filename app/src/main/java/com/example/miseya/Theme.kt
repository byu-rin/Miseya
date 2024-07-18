package com.example.miseya

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val notosanskr = FontFamily(
    Font(R.font.notosanskr_bold, FontWeight.Bold),
    Font(R.font.notosanskr_regular, FontWeight.Normal),
    Font(R.font.notosanskr_medium, FontWeight.Medium),
    Font(R.font.notosanskr_light, FontWeight.Light),
    Font(R.font.notosanskr_extralight, FontWeight.Thin)
)

val AppTypography = Typography(
    h1 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Bold,
    ),
    h2 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
    ),
    h3 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Medium,
    ),
    body1 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Light,
    ),
    body2 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Thin,
    )
)

@Composable
fun MiseyaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AppTypography,
        content = content
    )
}
