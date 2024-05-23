package com.example.to_dolist.presentation.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.gray
import com.example.to_dolist.presentation.theme.white

@Composable
fun DeleteAction(
    color: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        backgroundColor = color,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = white
            )
            Text(
                text = "Xóa",
                color = white,
                style = Typography.titleSmall,
            )
        }
    }
}

@Composable
fun PickDateAction(
    color: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        backgroundColor = color,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.DateRange,
                contentDescription = null,
                tint = white
            )
            Text(
                text = "Ngày",
                color = white,
                style = Typography.titleSmall,
            )
        }
    }

}