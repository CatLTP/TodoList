package com.example.to_dolist.presentation.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.to_dolist.R
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.darkGrey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarTaskItem(
    task: Task,
    onClickTask: (Task) -> Unit,
) {
    ListItem(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClickTask(task)
            },
        text = {
            Text(
                task.title,
                style = Typography.titleSmall,
                color = if (task.isFinished) darkGrey else black,
                textDecoration = if (task.isFinished) {
                    TextDecoration.LineThrough
                } else null
            )
        },
        secondaryText = {
            if (task.deadline != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_schedule_24),
                        contentDescription = "deadline icon",
                        tint = darkGrey
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = task.deadline!!,
                        color = darkGrey,
                        style = Typography.bodySmall
                    )
                }
            }
        },
    )
}