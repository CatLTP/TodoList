package com.example.to_dolist.domain.use_case.task

import android.net.Uri
import com.example.to_dolist.domain.repository.TaskRepository
import javax.inject.Inject

class SaveTaskImage @Inject constructor(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(imageList: List<Uri>): List<String> {
        return repository.saveImages(imageList)
    }
}