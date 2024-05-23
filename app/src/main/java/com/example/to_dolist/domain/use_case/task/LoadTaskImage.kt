package com.example.to_dolist.domain.use_case.task

import android.graphics.Bitmap
import com.example.to_dolist.domain.repository.TaskRepository
import javax.inject.Inject

class LoadTaskImage @Inject constructor(
    private val repository: TaskRepository,
) {

    operator fun invoke(imageList: List<String>): List<Bitmap> {
        return repository.loadImages(imageList)
    }
}