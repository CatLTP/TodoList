package com.example.to_dolist.presentation.task

import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.util.OrderType

sealed class TasksEvent {
    data class FilterByTag(val tagId: Int) : TasksEvent()
    data class DeleteTask(val task: Task) : TasksEvent()
    data class AddTask(val task: Task) : TasksEvent()
    data class SelectTagButton(val id: Int) : TasksEvent()
    data class ShowDatePicker(val shouldShow: Boolean) : TasksEvent()
    data class SelectTaskDeadline(val deadline: String) : TasksEvent()
    data class OnClickExpandTasks(val pos: Int) : TasksEvent()
    data class IsModifyingDeadline(val task: Task?): TasksEvent()
    data class OnFinishModifyingDeadline(val task: Task) : TasksEvent()
    data class OnClickSortTask(val isSort: Boolean) : TasksEvent()
    data class OnClickSearchTask(val isSearch: Boolean) : TasksEvent()
    data class OnClickSortCheckbox(val newValue : OrderType) : TasksEvent()
    data object OnFinishChoosingSortOption : TasksEvent()
    data class OnSearchValueChange(val newValue: String) : TasksEvent()
    data class OnClickFinishTask(val selectedTask: Task) : TasksEvent()
}