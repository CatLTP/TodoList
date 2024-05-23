package com.example.to_dolist.presentation.task.helper

import com.example.to_dolist.domain.model.Task

data class TaskSectionData(
    val taskList: List<Task>,
    val sectionName: String,
    val expandState: Boolean,
)