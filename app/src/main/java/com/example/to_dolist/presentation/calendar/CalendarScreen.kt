package com.example.to_dolist.presentation.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.to_dolist.presentation.common.CustomCalendarView
import com.example.to_dolist.domain.model.Screen
import com.example.to_dolist.presentation.calendar.components.CalendarTaskItem

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    state: CalendarState,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        CustomCalendarView(
            onDateSelected = {
                viewModel.onEvent(CalendarEvent.OnSelectDate(it.toString()))
            }
        )
        state.taskList.forEach {
            CalendarTaskItem(
                task = it,
                onClickTask = { task ->
                    navController.navigate(Screen.EditTask.route + "/${task.id}")
                },
            )
        }
    }
}

