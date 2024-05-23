package com.example.to_dolist.domain.use_case.task

import android.graphics.Bitmap
import com.example.to_dolist.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskImage @Inject constructor(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(fileName: String): Boolean {
        return repository.deleteImage(fileName)
    }
}