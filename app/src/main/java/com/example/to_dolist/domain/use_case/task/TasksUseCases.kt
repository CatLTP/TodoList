package com.example.to_dolist.domain.use_case.task

data class TasksUseCases(
    val getAllTask: GetAllTask,
    val getTask: GetTask,
    val deleteTask: DeleteTask,
    val addTask: AddTask,
    val updateTask: UpdateTask,
    val saveTaskImage: SaveTaskImage,
    val loadTaskImage: LoadTaskImage,
    val deleteTaskImage: DeleteTaskImage,
)