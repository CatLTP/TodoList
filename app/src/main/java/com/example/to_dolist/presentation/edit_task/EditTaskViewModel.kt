package com.example.to_dolist.presentation.edit_task

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.use_case.tag.TagUseCase
import com.example.to_dolist.domain.use_case.task.TasksUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val tagUseCase: TagUseCase,
    private val taskUseCase: TasksUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(EditTaskState())
    val state = _state.asStateFlow()
    private lateinit var currentTask : Task
    private var getTagJob: Job? = null

    init {
        getAllTags()
    }

    suspend fun onEvent(event: EditTaskEvent) {
        when (event) {
            is EditTaskEvent.OnLoadTask -> {
                getTask(event.taskId)
            }

            is EditTaskEvent.OnNoteChange -> {
                _state.value = _state.value.copy(
                    note = event.note
                )
            }

            is EditTaskEvent.OnChooseDeadline -> {
                _state.value = _state.value.copy(
                    taskDeadlineDate = event.deadline
                )
            }

            is EditTaskEvent.OnClickDeadline -> {
                _state.value = _state.value.copy(
                    showDeadlinePickerDialog = event.shouldShow
                )
            }

            is EditTaskEvent.OnClickExpandTagDropdown -> {
                _state.value = _state.value.copy(
                    expandTag = event.shouldExpand
                )
            }

            is EditTaskEvent.OnTitleChange -> {
                _state.value = _state.value.copy(
                    taskTitle = event.newTitle
                )
            }

            is EditTaskEvent.OnSelectNewTag -> {
                _state.value = _state.value.copy(
                    taskTagId = event.newTag.id
                )
            }

            is EditTaskEvent.ShowCustomRequestPermissionDialog -> {
                _state.value = _state.value.copy(
                    shouldShowCustomRequestPermissionDialog = event.shouldShow
                )
            }

            is EditTaskEvent.OnSaveImages -> {
                val imageNames = saveImages(event.imageList)
                val images = loadImages(imageNames)

                val allImagesName = mutableListOf<String>()
                _state.value.imageList?.let { allImagesName.addAll(it.toList()) }
                allImagesName.addAll(imageNames)

                val allImages = mutableListOf<Bitmap>()
                _state.value.images?.let { allImages.addAll(it.toList()) }
                allImages.addAll(images)

                _state.value = _state.value.copy(
                    imageList = allImagesName,
                    images = allImages,
                )
            }

            is EditTaskEvent.OnDeleteImage -> {
                val hasDeleted = deleteImage(event.imagePos)
                if (hasDeleted) {
                    val allImagesName = _state.value.imageList?.toMutableList()
                    val allImages = _state.value.images?.toMutableList()
                    allImagesName?.removeAt(event.imagePos)
                    allImages?.removeAt(event.imagePos)
                    _state.value = _state.value.copy(
                        imageList = allImagesName,
                        images = allImages
                    )
                }
            }

            is EditTaskEvent.OnNavigateBack -> {
                saveTask()
            }
        }
    }

    private fun loadImages(imageList: List<String>) : List<Bitmap> {
        return taskUseCase.loadTaskImage(imageList)
    }

    private suspend fun saveTask() {
        val task = currentTask.copy(
            title = _state.value.taskTitle,
            deadline = _state.value.taskDeadlineDate,
            tagId = _state.value.taskTagId,
            imageList = _state.value.imageList,
            note = _state.value.note
        )
        taskUseCase.updateTask(task)
    }

    private fun getAllTags() {
        getTagJob?.cancel()
        getTagJob = tagUseCase.getTag.invoke()
            .onEach { tags ->
                _state.value = _state.value.copy(
                    tagList = tags,
                )
            }
            .launchIn(viewModelScope)
    }

    private suspend fun saveImages(imagesList: List<Uri>) : List<String> {
        return taskUseCase.saveTaskImage(imagesList)
    }

    private suspend fun deleteImage(imagePos: Int) : Boolean {
        val fileName = _state.value.imageList?.get(imagePos)
        return if (fileName != null) {
            taskUseCase.deleteTaskImage(fileName)
        } else {
            false
        }
    }
    private fun getTask(taskId: Int) {
        viewModelScope.launch {
            currentTask = taskUseCase.getTask(taskId)
            val allImages = currentTask.imageList?.let { loadImages(it) }
            _state.value = _state.value.copy(
                taskTitle = currentTask.title,
                taskDeadlineDate = currentTask.deadline,
                taskTagId = currentTask.tagId,
                imageList = currentTask.imageList,
                images = allImages
            )
        }
    }
}