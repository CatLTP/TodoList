package com.example.to_dolist.domain.use_case.tag

import com.example.to_dolist.domain.use_case.tag.GetTag

data class TagUseCase(
    val getTag: GetTag,
    val addTag: AddTag,
    val deleteTag: DeleteTag,
    val updateTag: UpdateTag,
)