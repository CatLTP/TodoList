package com.example.to_dolist.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_dolist.domain.use_case.task.TasksUseCases
import com.example.to_dolist.domain.util.OrderType
import com.example.to_dolist.domain.util.TaskFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val tasksUseCases: TasksUseCases,
) : ViewModel() {

    private val _calendarState = MutableStateFlow(CalendarState())
    val calendarState = _calendarState.asStateFlow()

    private var getTaskJob: Job? = null

    init {
        getAllTask()
    }

    fun onEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.OnSelectDate -> {
                _calendarState.value = _calendarState.value.copy(
                    dateSelected = event.newDate,
                )
                getAllTask()
            }
        }
    }

    private fun getAllTask(
        taskFilter: TaskFilter =
            TaskFilter.Date(OrderType.DeadlineFirst, _calendarState.value.dateSelected)
    ) {
        getTaskJob?.cancel()
        getTaskJob = tasksUseCases.getAllTask(taskFilter)
            .onEach {
                _calendarState.value = _calendarState.value.copy(
                    taskList = it
                )
            }
            .launchIn(viewModelScope)
    }
}