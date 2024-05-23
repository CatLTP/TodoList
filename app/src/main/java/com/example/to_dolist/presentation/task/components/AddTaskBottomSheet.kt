package com.example.to_dolist.presentation.task.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.to_dolist.R
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.presentation.task.TaskState
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.gray
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.white
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDate

@Composable
fun AddTaskBottomSheet(
    onClickDatePicker: () -> Unit,
    onSaveTask: (Task) -> Unit,
    taskState: TaskState,
) {
    // Up Icon when expanded and down icon when collapsed
    var dropMenuExpanded by remember { mutableStateOf(false) }
    val icon = if (dropMenuExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    var selectedTag: Tag? by remember {
        mutableStateOf(null)
    }
    var taskTitle by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            value = taskTitle,
            onValueChange = { newText ->
                taskTitle = URLDecoder.decode(newText, "utf-8")
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = black
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.hint_add_task),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGrey
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = selectedTag?.name ?: "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    textFieldSize = it.size.toSize()
                },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Tag.kt Dropdown menu",
                    Modifier.clickable {
                        dropMenuExpanded = !dropMenuExpanded
                    }
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            placeholder = {
                Text(text = stringResource(R.string.hint_add_tag_for_task))
            },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownMenu(
            expanded = dropMenuExpanded,
            onDismissRequest = {
                dropMenuExpanded = false
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            taskState.tags.forEach { tag ->
                DropdownMenuItem(onClick = {
                    dropMenuExpanded = false
                    selectedTag = tag
                }) {
                    Text(text = tag.name)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            FilledIconButton(
                onClick = {
                    onClickDatePicker()
                }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = lightBlue
                ), modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Add deadline for task",
                    tint = white
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            FilledIconButton(
                onClick = {
                    if (taskTitle.isNotBlank()) {
                        val task = Task(
                            title = taskTitle,
                            tagId = selectedTag?.id,
                            dateCreate = LocalDate.now().toString(),
                            deadline = taskState.taskDeadline,

                            )
                        onSaveTask(task)
                    } else {
                        Toast.makeText(
                            context,
                            "Vui lòng nhập công việc",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = lightBlue
                ), modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Add task",
                    tint = white
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}