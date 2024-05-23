package com.example.to_dolist.presentation.edit_task

import android.graphics.Bitmap
import com.example.to_dolist.domain.model.Tag

data class EditTaskState(
    val expandTag: Boolean = false,
    val taskTitle: String = "",
    val taskDeadlineDate: String? = null,
    val taskDeadlineTime: String = "",
    val taskTagId: Int? = null,
    val note: String = "",
    val tagList: List<Tag> = emptyList(),
    val imageList: List<String>? = null,
    val images: List<Bitmap>? = null,
    val shouldShowCustomRequestPermissionDialog : Boolean = false,
    val showDeadlinePickerDialog : Boolean = false,
)