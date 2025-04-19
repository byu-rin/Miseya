package com.example.miseya

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val notosanskr = FontFamily(
    Font(R.font.notosanskr_bold, FontWeight.Bold),
    Font(R.font.notosanskr_regular, FontWeight.Normal),
    Font(R.font.notosanskr_medium, FontWeight.Medium),
    Font(R.font.notosanskr_light, FontWeight.Light),
    Font(R.font.notosanskr_extralight, FontWeight.Thin)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    displayMedium = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp
    )
)

@Composable
fun MiseyaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AppTypography,
        content = content
    )
}
