package com.example.to_dolist.presentation.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.use_case.tag.TagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagUseCase: TagUseCase,
): ViewModel() {
    private val _tagState = MutableStateFlow(TagState())
    val tagState = _tagState.asStateFlow()

    private var getTagJob: Job? = null

    init {
        getAllTags()
    }

    suspend fun onEvent(event: TagEvent) {
        when (event) {
            is TagEvent.AddTag -> {
                addTag(event.tag)
            }
            is TagEvent.OnClickMoreVert -> {
                _tagState.value = _tagState.value.copy(
                    expandedTagDropdown = event.pos
                )
            }
            is TagEvent.OnDeleteTag -> {
                deleteTag(event.tag)
                _tagState.value = _tagState.value.copy(
                    expandedTagDropdown = null
                )
            }
            is TagEvent.OnUpdateTag -> {
                updateTag(event.tag)
                _tagState.value = _tagState.value.copy(
                    showEditNameDialog = false
                )
            }

            is TagEvent.ShowAddTaskDialog -> {
                _tagState.value = _tagState.value.copy(
                    showAddTagDialog = event.shouldShow
                )
            }

            is TagEvent.ShowEditNameDialog -> {
                _tagState.value = _tagState.value.copy(
                    showEditNameDialog = event.shouldShow,
                    editingTag = event.editingTag
                )
            }
        }
    }

    private fun getAllTags() {
        getTagJob?.cancel()
        getTagJob = tagUseCase.getTag.invoke()
            .onEach { tags ->
                _tagState.value = tagState.value.copy(
                    tags = tags,
                )
            }
            .launchIn(viewModelScope)
    }

    private suspend fun addTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.addTag.invoke(tag)
        }
        _tagState.value = _tagState.value.copy(
            showAddTagDialog = false
        )
    }

    private suspend fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.deleteTag.invoke(tag)
        }
    }

    private suspend fun updateTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.updateTag.invoke(tag)
        }
    }
}