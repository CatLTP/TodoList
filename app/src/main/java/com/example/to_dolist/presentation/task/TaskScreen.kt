package com.example.to_dolist.presentation.task

import android.content.Context
import android.Manifest.permission.POST_NOTIFICATIONS
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.to_dolist.R
import com.example.to_dolist.presentation.common.MyDatePicker
import com.example.to_dolist.domain.model.Screen
import com.example.to_dolist.presentation.task.components.AddTaskBottomSheet
import com.example.to_dolist.presentation.task.components.AppBar
import com.example.to_dolist.presentation.task.components.SortDialog
import com.example.to_dolist.presentation.task.components.TaskSection
import com.example.to_dolist.presentation.task.helper.TaskAppBarMenu
import com.example.to_dolist.presentation.task.helper.TaskSectionData
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.white
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun TaskScreen(
    context: Context,
    viewModel: TaskViewModel,
    taskState: TaskState,
    navController: NavController,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val notificationPermissionState =
            rememberPermissionState(permission = POST_NOTIFICATIONS)
        var showBottomSheet by remember {
            mutableStateOf(false)
        }
        val sheetState = rememberModalBottomSheetState()
        // Value for storing time as a string
        var timePicker by remember { mutableStateOf<String?>(null) }
        val composableScope = rememberCoroutineScope()
        val list = listOf(
            TaskSectionData(
                taskState.futureTasks,
                stringResource(R.string.future),
                taskState.expandFutureTask,
            ),
            TaskSectionData(
                taskState.todayTasks,
                stringResource(R.string.today),
                taskState.expandTodayTask,
            ),
            TaskSectionData(
                taskState.pastTasks,
                stringResource(R.string.before),
                taskState.expandPastTask,
            ),
            TaskSectionData(
                taskState.doneTasks,
                stringResource(R.string.finish_task),
                taskState.expandDoneTask,
            ),
        )

        
        LaunchedEffect(key1 = notificationPermissionState.status) {
            if (!notificationPermissionState.status.isGranted) {
                notificationPermissionState.launchPermissionRequest()
            }
        }
        Scaffold(
            topBar = {
                AppBar(
                    taskState,
                    onClickTagButton = { pos ->
                        composableScope.launch {
                            viewModel.onEvent(TasksEvent.SelectTagButton(pos))
                        }
                    },
                    onClickBackButtonInSearchMode = {
                        composableScope.launch {
                            viewModel.onEvent(TasksEvent.OnClickSearchTask(false))
                        }
                    },
                    onSearchValueChange = { newValue ->
                        composableScope.launch {
                            viewModel.onEvent(TasksEvent.OnSearchValueChange(newValue))
                        }
                    },
                    onClickAppBarMenu = { option ->
                        when (option) {
                            TaskAppBarMenu.SearchTask -> {
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.OnClickSearchTask(true))
                                }
                            }

                            TaskAppBarMenu.TagManagement -> {
                                navController.navigate(Screen.TagManagement.route)
                            }

                            TaskAppBarMenu.SortTask -> {
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.OnClickSortTask(true))
                                }
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    shape = CircleShape,
                    containerColor = lightBlue,
                    modifier = Modifier.size(60.dp),
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add task",
                            tint = white
                        )
                    }
                )
            },
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    item {
                        for (i in list.indices) {
                            TaskSection(
                                taskList = list[i].taskList,
                                sectionName = list[i].sectionName,
                                expandState = list[i].expandState,
                                onClickCollapseSection = {
                                    composableScope.launch {
                                        viewModel.onEvent(TasksEvent.OnClickExpandTasks(i))
                                    }
                                },
                                onClickDelete = {
                                    composableScope.launch {
                                        viewModel.onEvent(TasksEvent.DeleteTask(it))
                                    }
                                },
                                onClickDatePicker = {
                                    composableScope.launch {
                                        viewModel.onEvent(TasksEvent.ShowDatePicker(true))
                                        viewModel.onEvent(TasksEvent.IsModifyingDeadline(it))
                                    }
                                },
                                onClickFinishTask = {
                                    composableScope.launch {
                                        viewModel.onEvent(TasksEvent.OnClickFinishTask(it))
                                    }
                                },
                                onClickTaskItem = { taskId ->
                                    navController.navigate(Screen.EditTask.route + "/${taskId}")
                                }
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        sheetState = sheetState,
                    ) {
                        AddTaskBottomSheet(
                            onClickDatePicker = {
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.ShowDatePicker(true))
                                }
                            },
                            onSaveTask = {
                                val task = it.copy(
                                    timeDeadline = timePicker
                                )
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.AddTask(task))
                                }.invokeOnCompletion {
                                    showBottomSheet = false
                                    timePicker = null
                                }
                            },
                            taskState = taskState
                        )
                    }
                }

                if (taskState.showDatePicker) {
                    Dialog(
                        onDismissRequest = {
                            composableScope.launch {
                                viewModel.onEvent(TasksEvent.ShowDatePicker(false))
                            }
                        },
                    ) {
                        MyDatePicker(
                            onDateSelected = {
                                composableScope.launch {
                                    if (taskState.modifyingDeadline != null) {
                                        val task = taskState.modifyingDeadline.copy(
                                            deadline = it.toString()
                                        )
                                        viewModel.onEvent(TasksEvent.OnFinishModifyingDeadline(task))
                                    } else {
                                        viewModel.onEvent(TasksEvent.SelectTaskDeadline(it.toString()))
                                    }
                                }
                            },
                            onDismissRequest = {
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.IsModifyingDeadline(null))
                                    viewModel.onEvent(TasksEvent.ShowDatePicker(false))
                                }
                            },
                            context = context,
                            onChooseTime = {
                                composableScope.launch {
                                    if (taskState.modifyingDeadline != null) {
                                        val task = taskState.modifyingDeadline.copy(
                                            timeDeadline = it
                                        )
                                        viewModel.onEvent(TasksEvent.OnFinishModifyingDeadline(task))
                                    } else {
                                        timePicker = it
                                    }
                                }
                            }
                        )
                    }
                }

                if (taskState.showSortDialog) {
                    Dialog(
                        onDismissRequest = {
                            composableScope.launch {
                                viewModel.onEvent(TasksEvent.OnClickSortTask(false))
                            }
                        },
                        properties = DialogProperties(),
                    ) {
                        SortDialog(
                            taskState = taskState,
                            onCheckChange = {
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.OnClickSortCheckbox(it))
                                }
                            },
                            onFinishChoosing = {
                                composableScope.launch {
                                    viewModel.onEvent(TasksEvent.OnClickSortTask(false))
                                    viewModel.onEvent(TasksEvent.OnFinishChoosingSortOption)
                                }
                            }
                        )
                    }
                }
            },
        )
    }
}
