package com.example.to_dolist.domain.use_case.tag

import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTag @Inject constructor(
    private val tagRepository: TagRepository
) {

    operator fun invoke() : Flow<List<Tag>> {
        return tagRepository.getAllTag()
    }
}