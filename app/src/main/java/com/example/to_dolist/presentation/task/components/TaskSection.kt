package com.example.to_dolist.presentation.task.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.lightBlue

@Composable
fun TaskSection(
    taskList: List<Task>,
    sectionName: String,
    expandState: Boolean,
    onClickFinishTask: (selectedTask: Task) -> Unit,
    onClickCollapseSection: () -> Unit,
    onClickDelete: (task: Task) -> Unit,
    onClickDatePicker: (task: Task) -> Unit,
    onClickTaskItem: (taskId: Int) -> Unit,
) {
    if (taskList.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onClickCollapseSection) {
                Row {
                    Text(
                        text = sectionName,
                        fontSize = 16.sp,
                        color = black
                    )
                    Icon(
                        imageVector = if (expandState) {
                            Icons.Filled.KeyboardArrowDown
                        } else {
                            Icons.Filled.KeyboardArrowUp
                        },
                        contentDescription = "Dropdown",
                        tint = lightBlue
                    )
                }
            }
        }

        AnimatedVisibility(visible = expandState) {
            Column(
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                taskList.forEach {
                    key(it.id) {
                        TaskItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 10.dp
                                )
                                .clip(CircleShape),
                            task = it,
                            onClickDelete = {
                                onClickDelete(it)
                            },
                            onClickFinishTask = { selectedTask ->
                                onClickFinishTask(selectedTask)
                            },
                            onClickDatePicker = {
                                onClickDatePicker(it)
                            },
                            onClickTaskItem = onClickTaskItem,
                        )
                    }

                }
            }
        }
    }
}