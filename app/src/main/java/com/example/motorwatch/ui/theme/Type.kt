package com.example.motorwatch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    // Labels for bodySmall text, title for chat screen, or medium sized text
    bodyMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    // Labels for bodySmall text fields or small sized text
    bodySmall = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    // Location label for claim card widget
    headlineSmall = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    // Labels for sections or important (to be highlighted) medium sized text
    headlineMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    // Labels for policy card widgets
    headlineLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    // Titles for sections (child composables like Cards)
    titleMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    // Titles for entire Screens
    titleLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        color = ReducedBlack,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
)