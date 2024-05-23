package com.example.to_dolist.presentation.task

import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.util.OrderType
import com.example.to_dolist.domain.util.TaskFilter

data class TaskState(
    val todayTasks: List<Task> = emptyList(),
    val futureTasks: List<Task> = emptyList(),
    val pastTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val selectedTag: Int = 1,
    val selectedSortOption: OrderType = OrderType.AscendingLetter,
    val taskFilter: TaskFilter = TaskFilter.Tag(selectedSortOption, null),
    val selectedDeadline: String? = null,
    val showDatePicker: Boolean = false,
    val taskDeadline: String? = null,
    val expandFutureTask: Boolean = true,
    val expandPastTask: Boolean = true,
    val expandTodayTask: Boolean = true,
    val expandDoneTask: Boolean = true,
    val modifyingDeadline: Task? = null,
    val showSortDialog: Boolean = false,
    val showSearchBar: Boolean = false,
    val searchValue: String = "",
)
