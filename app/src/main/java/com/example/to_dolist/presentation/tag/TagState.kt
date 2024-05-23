package com.example.to_dolist.presentation.tag

import com.example.to_dolist.domain.model.Tag

data class TagState(
    val tags: List<Tag> = emptyList(),
    val expandedTagDropdown : Tag? = null,
    val showAddTagDialog: Boolean = false,
    val editingTag: Tag? = null,
    val showEditNameDialog: Boolean = false,
)