package com.example.to_dolist.domain.use_case.tag

import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.repository.TagRepository
import javax.inject.Inject

class DeleteTag @Inject constructor(
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(tag: Tag) {
        return tagRepository.deleteTag(tag)
    }
}