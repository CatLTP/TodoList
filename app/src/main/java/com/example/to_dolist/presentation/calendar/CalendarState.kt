package com.example.to_dolist.presentation.calendar

import com.example.to_dolist.domain.model.Task
import java.time.LocalDate

data class CalendarState(
    val dateSelected: String = LocalDate.now().toString(),
    val taskList: List<Task> = emptyList(),
)