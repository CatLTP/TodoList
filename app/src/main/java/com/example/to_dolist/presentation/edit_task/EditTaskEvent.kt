package com.example.to_dolist.presentation.edit_task

import android.net.Uri
import com.example.to_dolist.domain.model.Tag

sealed class EditTaskEvent {
    data class OnLoadTask(val taskId: Int) : EditTaskEvent()
    data class OnClickExpandTagDropdown(val shouldExpand: Boolean) : EditTaskEvent()
    data class OnTitleChange(val newTitle: String) : EditTaskEvent()
    data class OnSelectNewTag(val newTag: Tag) : EditTaskEvent()
    data class ShowCustomRequestPermissionDialog(val shouldShow: Boolean) : EditTaskEvent()
    data class OnSaveImages(val imageList: List<Uri>) : EditTaskEvent()
    data class OnDeleteImage(val imagePos: Int) : EditTaskEvent()
    data object OnNavigateBack : EditTaskEvent()
    data class OnNoteChange(val note: String) : EditTaskEvent()
    data class OnClickDeadline(val shouldShow: Boolean): EditTaskEvent()
    data class OnChooseDeadline(val deadline: String): EditTaskEvent()
}