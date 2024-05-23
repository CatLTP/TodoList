package com.example.to_dolist.presentation.task.components

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.to_dolist.R
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.gray
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.red
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    modifier: Modifier,
    task: Task,
    onClickFinishTask: (selectedTask: Task) -> Unit,
    onClickDelete: () -> Unit,
    onClickDatePicker: () -> Unit,
    onClickTaskItem: (taskId: Int) -> Unit,
) {
    val density = LocalDensity.current
    val screenSizeDp = LocalConfiguration.current.screenWidthDp.dp
    val screenSizePx = with(density) { screenSizeDp.toPx() }
    val defaultActionSize = screenSizeDp / 4
    val anchors = remember {
        DraggableAnchors {
            Start at 0f
            End at screenSizePx / 2
        }
    }
    val swipeState = remember {
        AnchoredDraggableState(
            initialValue = Start,
            positionalThreshold = { totalDistance : Float ->
                totalDistance * 0.5f
            },
            velocityThreshold = {
                with(density) {
                    100.dp.toPx()
                }
            },
            animationSpec = tween()
        )
    }
    SideEffect {
        swipeState.updateAnchors(anchors)
    }
    Box(
        modifier = modifier,
    ) {
        Surface(
            color = gray,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .anchoredDraggable(
                    swipeState,
                    Orientation.Horizontal,
                    reverseDirection = true
                )
                .offset {
                    IntOffset(
                        x = -swipeState.offset.absoluteValue.roundToInt(),
                        y = 0,
                    )
                }
                .clickable {
                    onClickTaskItem(task.id)
                },
        ) {
            ListItem(
                modifier = Modifier.padding(8.dp),
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
                icon = {
                    if (!task.isFinished) {
                        OutlinedButton(
                            onClick = {
                                onClickFinishTask(task)
                            },
                            shape = CircleShape,
                            modifier = Modifier.size(30.dp),
                            border = BorderStroke(
                                width = 1.dp,
                                color = darkGrey
                            )
                        ) {
                        }
                    } else {
                        IconButton(onClick = { onClickFinishTask(task) }) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Bookmark",
                                tint = darkGrey
                            )
                        }
                    }
                },
                trailing = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_bookmark),
                            contentDescription = "Bookmark",
                            tint = darkGrey
                        )
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .width(defaultActionSize)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        ) {
            DeleteAction(
                color = red,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .offset {
                        IntOffset(
                            ((-swipeState
                                .requireOffset() + defaultActionSize
                                .toPx()
                                .roundToInt()))
                                .roundToInt(), 0
                        )
                    }
                    .clickable {
                        onClickDelete()
                    }
            )
            PickDateAction(
                color = lightBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .offset {
                        IntOffset(
                            ((-swipeState
                                .requireOffset() / 2) + defaultActionSize
                                .toPx()
                                .roundToInt())
                                .roundToInt(), 0
                        )
                    }
                    .clickable {
                        onClickDatePicker()
                    },
            )
        }
    }
}
