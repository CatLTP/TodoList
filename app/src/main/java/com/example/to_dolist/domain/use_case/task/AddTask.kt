package com.example.to_dolist.domain.use_case.task

import com.example.to_dolist.domain.model.InvalidTaskException
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.repository.TaskRepository

class AddTask(
    private val repository: TaskRepository
) {

    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task) {
        if (task.title.isBlank()) {
            throw InvalidTaskException("Vui lòng nhập công việc")
        }
        repository.addTask(task)
    }
}