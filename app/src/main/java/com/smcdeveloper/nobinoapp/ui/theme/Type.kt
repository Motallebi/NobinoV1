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


val font_medium= FontFamily(

    Font(R.font.iranyekanmedium)


)


val font_bold = FontFamily(
    Font(R.font.iranyekanbold)
)
val font_standard = FontFamily(
    Font(R.font.iranyekan)
)



val Typography.extraBoldNumber : TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
    )

val Typography.nobinoLarge : TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        color = Color.White
    )

val Typography.nobinoMedium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White,


    )




// Set of Material typography styles to start with
val Typography = Typography(
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
