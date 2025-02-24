package com.smcdeveloper.nobinoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.smcdeveloper.nobinoapp.R




  //Adding Custom Fonts To the Nobino App


val nobino_fonts= FontFamily(

    Font(R.font.iransans_mobile)


)


val font_bold = FontFamily(
    Font(R.font.iransans_bold)
)
val font_standard = FontFamily(
    Font(R.font.iransans_medium)
)



val Typography.extraBoldNumber : TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = nobino_fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
    )

val Typography.nobinoLarge : TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = nobino_fonts,
        fontWeight = FontWeight.Bold,
        fontSize =20.sp,
        color = Color.White
    )

val Typography.nobinoMedium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = nobino_fonts,
        fontWeight = FontWeight.Bold,
        fontSize =16.sp,
        color = Color.White,


    )

val Typography.nobinoMediumLight: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = nobino_fonts,
        fontWeight = FontWeight.Bold,
        fontSize =16.sp,
        color = Color.White.copy(alpha =0.5f),


        )






val Typography.nobinoSmall: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = nobino_fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color.White,


        )




// Set of Material typography styles to start with
val AppTypography = Typography(
        bodyLarge = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = font_standard,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 25.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 25.sp
    ),
    labelMedium = TextStyle(
     fontFamily = font_standard,
        fontWeight = FontWeight.Normal,



    ),









)
























    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
