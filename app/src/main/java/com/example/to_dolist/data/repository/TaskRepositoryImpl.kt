package com.example.to_dolist.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.to_dolist.data.source.ImageHandler
import com.example.to_dolist.data.source.TaskDao
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val imageHandler: ImageHandler,
) : TaskRepository {

    override fun getAllTask() : Flow<List<Task>> {
        return taskDao.getAllTask()
    }

    override suspend fun addTask(task: Task) {
        return taskDao.addTask(task)
    }

    override suspend fun getTaskById(id: Int): Task {
        return taskDao.getTaskById(id)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        return taskDao.updateTask(task)
    }

    override suspend fun saveImages(imageList: List<Uri>) : List<String> {
        return imageHandler.saveImage(imageList)
    }

    override fun loadImages(imageName: List<String>): List<Bitmap> {
        return imageHandler.loadImage(imageName)
    }

    override suspend fun deleteImage(imageName: String): Boolean {
        return imageHandler.deleteImage(fileName = imageName)
    }
}