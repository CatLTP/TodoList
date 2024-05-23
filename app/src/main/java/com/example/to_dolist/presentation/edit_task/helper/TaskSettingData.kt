package com.example.to_dolist.presentation.edit_task.helper

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class TaskSettingData(
    val title: String,
    val painter: Painter,
    val value: String,
    val onClick: () -> Unit,
)
