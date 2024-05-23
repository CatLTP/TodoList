package com.example.to_dolist.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.to_dolist.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTask(): Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun getTaskById(id: Int): Task

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun saveImages(imageList: List<Uri>) : List<String>

    fun loadImages(imageName: List<String>) : List<Bitmap>

    suspend fun deleteImage(imageName: String) : Boolean
}