package com.example.to_dolist.presentation.task.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.gray


@Composable
fun TagButtonItem(
    tag: Tag,
    textColor: Color = darkGrey,
    buttonColor: Color = gray,
    onClick: () -> Unit
) {

    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(buttonColor)) {
        Text(text = tag.name, style = Typography.titleSmall, color = textColor)
    }
}