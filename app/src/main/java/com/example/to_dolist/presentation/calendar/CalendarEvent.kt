package com.example.to_dolist.presentation.calendar

sealed class CalendarEvent {
    data class OnSelectDate(val newDate: String) : CalendarEvent()
}