package com.example.to_dolist.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    data object Task: BottomBarScreen(
        route = "overview",
        title = "Nhiệm vụ",
        icon = Icons.Default.Home
    )
    data object Calendar: BottomBarScreen(
        route = "calendar",
        title = "Lịch",
            icon = Icons.Default.DateRange
    )
    data object Profile: BottomBarScreen(
        route = "profile",
        title = "Của tôi",
        icon = Icons.Default.Person
    )
}