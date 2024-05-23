package com.example.to_dolist.presentation.edit_task

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.to_dolist.PACKAGE_NAME
import com.example.to_dolist.R
import com.example.to_dolist.presentation.common.MyDatePicker
import com.example.to_dolist.domain.model.Screen
import com.example.to_dolist.presentation.edit_task.components.RequestMediaPermissionDialog
import com.example.to_dolist.presentation.edit_task.components.TaskImages
import com.example.to_dolist.presentation.edit_task.components.TaskSettingItem
import com.example.to_dolist.presentation.edit_task.helper.TaskSettingData
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.gray
import com.example.to_dolist.presentation.theme.white
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditTaskScreen(
    taskId: Int,
    viewModel: EditTaskViewModel,
    navController: NavController,
    state: EditTaskState,
) {
    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()

    val imagePermissionState =
        rememberPermissionState(permission = READ_MEDIA_IMAGES)
    val getMultipleImagesLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetMultipleContents()
    ) { list ->
        coroutineScope.launch {
            viewModel.onEvent(EditTaskEvent.OnSaveImages(list))
        }
    }

    val settings = listOf(
        TaskSettingData(
            title = stringResource(R.string.deadline),
            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
            value = state.taskDeadlineDate ?: stringResource(id = R.string.no),
            onClick = {
                coroutineScope.launch {
                    viewModel.onEvent(EditTaskEvent.OnClickDeadline(true))
                }
            }
        ),
        TaskSettingData(
            title = "Thời gian & Lời nhắc",
            painter = painterResource(id = R.drawable.baseline_time),
            value = stringResource(id = R.string.no),
            onClick = {

            }
        ),
        TaskSettingData(
            title = "Lặp lại nhiệm vụ",
            painter = painterResource(id = R.drawable.baseline_loop_24),
            value = stringResource(id = R.string.no),
            onClick = {

            }
        ),
        TaskSettingData(
            title = "Ghi chú",
            painter = painterResource(id = R.drawable.baseline_edit_note_24),
            value = stringResource(id = R.string.no),
            onClick = {
                navController.navigate(Screen.TaskNote.route)
            }
        ),
        TaskSettingData(
            title = "Tập tin đính kèm",
            painter = painterResource(id = R.drawable.baseline_attach_file_24),
            value = stringResource(id = R.string.no),
            onClick = {
                when (imagePermissionState.status) {
                    is PermissionStatus.Granted -> {
                        getMultipleImagesLauncher.launch("image/*")
                    }

                    is PermissionStatus.Denied -> {
                        coroutineScope.launch {
                            viewModel.onEvent(
                                EditTaskEvent.ShowCustomRequestPermissionDialog(
                                    true
                                )
                            )
                        }
                    }
                }
            }
        ),
    )

    LaunchedEffect(taskId) {
        viewModel.onEvent(EditTaskEvent.OnLoadTask(taskId))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = white,
                elevation = 0.dp,
                title = {
                    Text(text = Screen.EditTask.title, style = Typography.titleMedium)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.onEvent(EditTaskEvent.OnNavigateBack)
                            }.invokeOnCompletion {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "navigate back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Action",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .verticalScroll(
                    rememberScrollState()
                ),
        ) {
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape),
                onClick = {
                    coroutineScope.launch {
                        viewModel.onEvent(EditTaskEvent.OnClickExpandTagDropdown(true))
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = gray
                )
            ) {
                Row {
                    Text(
                        text = if (state.taskTagId != null && state.taskTagId != 0) state.tagList.first {
                            it.id == state.taskTagId
                        }.name else "Không có thể loại",
                        color = darkGrey,
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "navigate back",
                        modifier = Modifier.size(30.dp),
                        tint = darkGrey
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .wrapContentWidth(),
                    expanded = state.expandTag,
                    onDismissRequest = {
                        coroutineScope.launch {
                            viewModel.onEvent(EditTaskEvent.OnClickExpandTagDropdown(false))
                        }
                    },
                ) {
                    state.tagList.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = it.name)
                            },
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.onEvent(EditTaskEvent.OnSelectNewTag(it))
                                    viewModel.onEvent(EditTaskEvent.OnClickExpandTagDropdown(false))
                                }
                            },
                        )
                    }
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 250.dp
                    )
                    .padding(10.dp),
                value = state.taskTitle,
                onValueChange = {
                    coroutineScope.launch {
                        viewModel.onEvent(EditTaskEvent.OnTitleChange(it))
                    }
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = black
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            settings.forEach {
                TaskSettingItem(
                    data = it
                )
            }

            if (!state.images.isNullOrEmpty()) {
                TaskImages(
                    imageList = state.images,
                    onDeleteImage = {
                        coroutineScope.launch {
                            viewModel.onEvent(EditTaskEvent.OnDeleteImage(it))
                        }
                    },
                )
            }
        }

        if (state.shouldShowCustomRequestPermissionDialog) {
            RequestMediaPermissionDialog(
                onDismissRequest = {
                    coroutineScope.launch {
                        viewModel.onEvent(
                            EditTaskEvent.ShowCustomRequestPermissionDialog(false)
                        )
                    }
                },
                onDecline = {
                    coroutineScope.launch {
                        viewModel.onEvent(
                            EditTaskEvent.ShowCustomRequestPermissionDialog(false)
                        )
                    }
                },
                onAgree = {
                    if (imagePermissionState.status.shouldShowRationale) {
                        imagePermissionState.launchPermissionRequest()
                    } else {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$PACKAGE_NAME"),
                        )
                        activity.startActivity(intent)
                    }
                    coroutineScope.launch {
                        viewModel.onEvent(
                            EditTaskEvent.ShowCustomRequestPermissionDialog(
                                false
                            )
                        )
                    }
                },
            )
        }

        if (state.showDeadlinePickerDialog) {
            MyDatePicker(
                onDateSelected = {
                    coroutineScope.launch {
                        viewModel.onEvent(EditTaskEvent.OnClickDeadline(false))
                    }
                },
                onDismissRequest = {
                    coroutineScope.launch {
                        viewModel.onEvent(EditTaskEvent.OnClickDeadline(false))
                    }
                },
                context = LocalContext.current,
                onChooseTime = {

                }
            )
        }
    }
}

