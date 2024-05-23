package com.example.to_dolist.presentation.task

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.to_dolist.domain.background.ReminderWorker
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.use_case.tag.TagUseCase
import com.example.to_dolist.domain.use_case.task.TasksUseCases
import com.example.to_dolist.domain.util.OrderType
import com.example.to_dolist.domain.util.TaskFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val tasksUseCases: TasksUseCases,
    private val tagUseCase: TagUseCase,
    private val workManager: WorkManager,
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())
    val taskState = _taskState.asStateFlow()

    private var getTaskJob: Job? = null
    private var getTagJob: Job? = null

    init {
        getAllTask(TaskFilter.Tag(_taskState.value.selectedSortOption, null))
        getAllTags()
    }

    suspend fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.DeleteTask -> {
                deleteTask(event.task)
            }

            is TasksEvent.FilterByTag -> {
                if ((taskState.value.taskFilter as TaskFilter.Tag).tagId == event.tagId) {
                    return
                }
                getAllTask(
                    TaskFilter.Tag(
                        _taskState.value.selectedSortOption, event.tagId,
                    )
                )
            }

            is TasksEvent.AddTask -> {
                addTask(event.task)
            }

            is TasksEvent.SelectTagButton -> {
                _taskState.value = _taskState.value.copy(
                    selectedTag = event.id
                )
                getAllTask(
                    TaskFilter.Tag(
                        OrderType.AscendingLetter,
                        if (event.id == 0) null else event.id
                    )
                )
            }

            is TasksEvent.ShowDatePicker -> {
                _taskState.value = _taskState.value.copy(
                    showDatePicker = event.shouldShow
                )
            }

            is TasksEvent.SelectTaskDeadline -> {
                _taskState.value = _taskState.value.copy(
                    taskDeadline = event.deadline
                )
            }

            is TasksEvent.OnClickExpandTasks -> {
                when (event.pos) {
                    0 -> _taskState.value = _taskState.value.copy(
                        expandFutureTask = !_taskState.value.expandFutureTask
                    )

                    1 -> _taskState.value = _taskState.value.copy(
                        expandTodayTask = !_taskState.value.expandTodayTask
                    )

                    else -> _taskState.value = _taskState.value.copy(
                        expandPastTask = !_taskState.value.expandPastTask
                    )
                }
            }

            is TasksEvent.IsModifyingDeadline -> {
                _taskState.value = _taskState.value.copy(
                    modifyingDeadline = event.task
                )
            }

            is TasksEvent.OnFinishModifyingDeadline -> {
                updateTask(task = event.task)
            }

            is TasksEvent.OnClickSortTask -> {
                _taskState.value = _taskState.value.copy(
                    showSortDialog = event.isSort
                )
            }

            is TasksEvent.OnClickSearchTask -> {
                _taskState.value = _taskState.value.copy(
                    showSearchBar = event.isSearch
                )
                if (!event.isSearch) {
                    getAllTask(
                        TaskFilter.Tag(
                            _taskState.value.selectedSortOption,
                            null,
                        )
                    )
                }
            }

            is TasksEvent.OnClickSortCheckbox -> {
                _taskState.value = _taskState.value.copy(
                    selectedSortOption = event.newValue
                )
            }

            is TasksEvent.OnFinishChoosingSortOption -> {
                getAllTask(
                    TaskFilter.Tag(
                        _taskState.value.selectedSortOption,
                        null,
                    )
                )
            }

            is TasksEvent.OnSearchValueChange -> {
                _taskState.value = _taskState.value.copy(
                    searchValue = event.newValue
                )
                getAllTask(
                    TaskFilter.Title(
                        _taskState.value.selectedSortOption,
                        _taskState.value.searchValue,
                    )
                )
            }

            is TasksEvent.OnClickFinishTask -> {
                val task = event.selectedTask.copy(
                    isFinished = !event.selectedTask.isFinished
                )
                updateTask(task = task)
            }
        }
    }

    private fun getAllTask(
        taskFilter: TaskFilter
    ) {
        getTaskJob?.cancel()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        getTaskJob = tasksUseCases.getAllTask(taskFilter)
            .onEach { tasks ->
                val current = sdf.parse(LocalDate.now().toString())
                val todayTasks: MutableList<Task> = mutableListOf()
                val futureTasks: MutableList<Task> = mutableListOf()
                val pastTasks: MutableList<Task> = mutableListOf()
                val doneTasks: MutableList<Task> = mutableListOf()

                for (task in tasks) {
                    if (task.isFinished) {
                        doneTasks.add(task)
                    } else {
                        if (task.deadline != null) {
                            val deadline = sdf.parse(task.deadline)
                            val result = deadline.compareTo(current)
                            when {
                                result > 0 -> {
                                    futureTasks.add(task)
                                }

                                result < 0 -> {
                                    pastTasks.add(task)
                                }

                                else -> {
                                    todayTasks.add(task)
                                }
                            }
                        } else {
                            todayTasks.add(task)
                        }
                    }
                }
                _taskState.value = taskState.value.copy(
                    todayTasks = todayTasks,
                    pastTasks = pastTasks,
                    futureTasks = futureTasks,
                    doneTasks = doneTasks,
                    taskFilter = taskFilter
                )
            }.launchIn(viewModelScope)
    }

    private suspend fun addTask(task: Task) {
        viewModelScope.launch {
            tasksUseCases.addTask(task)
            scheduleReminder(task)
        }
    }

    private suspend fun updateTask(task: Task) {
        viewModelScope.launch {
            tasksUseCases.updateTask(task)
        }
    }

    private suspend fun deleteTask(task: Task) {
        viewModelScope.launch {
            tasksUseCases.deleteTask.invoke(task)
        }
    }

    private fun getAllTags() {
        getTagJob?.cancel()
        getTagJob = tagUseCase.getTag.invoke()
            .onEach { tags ->
                _taskState.value = _taskState.value.copy(
                    tags = tags,
                    selectedTag = 0
                )
            }
            .launchIn(viewModelScope)
    }

    private fun scheduleReminder(task: Task) {
        if (task.deadline != null) {
            val workRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()
            workRequestBuilder.setInputData(
                workDataOf(
                    ReminderWorker.TITLE_KEY to task.title,
                    ReminderWorker.CONTENT_KEY to task.note,
                )
            )
            val deadline = if (task.timeDeadline != null)
                OffsetDateTime.parse(task.deadline + "T" + task.timeDeadline + ":00.000Z")
            else OffsetDateTime.parse(task.deadline + "T09:00:00.000Z")
            val duration = Duration.between(LocalDateTime.now(), deadline)

            workRequestBuilder.setInitialDelay(duration.toMinutes(), TimeUnit.MINUTES)
            workManager.enqueue(workRequestBuilder.build())
        }
    }
}