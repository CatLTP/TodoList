package com.example.to_dolist.domain.use_case.task

import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.repository.TaskRepository

class UpdateTask(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
    }
}