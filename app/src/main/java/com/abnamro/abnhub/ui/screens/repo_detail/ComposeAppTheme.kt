package com.abnamro.abnhub.ui.screens.repo_detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abnamro.abnhub.R

private val robotoFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_thin, FontWeight.ExtraLight),
)

val Typography = Typography(
    defaultFontFamily = robotoFontFamily,
)

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun ComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val lightColorPalette = lightColors(
        primary = colorResource(id = R.color.primary),
        primaryVariant = colorResource(id = R.color.primary_dark),
        onPrimary = colorResource(id = R.color.white),
        secondary = colorResource(id = R.color.secondary),
        secondaryVariant = colorResource(id = R.color.secondary_dark),
        onSecondary = colorResource(id = R.color.black),
        surface = colorResource(id = R.color.white),
        onSurface = colorResource(id = R.color.black),
        background = colorResource(id = R.color.white),
        onBackground = colorResource(id = R.color.black),
    )

    val darkColorPalette = darkColors(
        primary = colorResource(id = R.color.primary_light),
        primaryVariant = colorResource(id = R.color.primary_dark),
        onPrimary = colorResource(id = R.color.white),
        secondary = colorResource(id = R.color.secondary),
        secondaryVariant = colorResource(id = R.color.secondary),
        onSecondary = colorResource(id = R.color.black),
        surface = colorResource(id = R.color.dark_gray),
        onSurface = colorResource(id = R.color.white),
        background = colorResource(id = R.color.black),
        onBackground = colorResource(id = R.color.white),
    )

    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    MaterialTheme(
        typography = Typography,
        colors = colors,
        shapes = Shapes,
    ) {
        content()
    }
}
