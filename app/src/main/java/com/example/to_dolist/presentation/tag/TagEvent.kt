package com.example.to_dolist.presentation.tag

import com.example.to_dolist.domain.model.Tag

sealed class TagEvent {
    data class AddTag(val tag: Tag) : TagEvent()
    data class OnClickMoreVert(val pos: Tag?) : TagEvent()
    data class OnUpdateTag(val tag: Tag) : TagEvent()
    data class OnDeleteTag(val tag: Tag) : TagEvent()
    data class ShowAddTaskDialog(val shouldShow: Boolean) : TagEvent()
    data class ShowEditNameDialog(val editingTag: Tag?, val shouldShow: Boolean) : TagEvent()
}