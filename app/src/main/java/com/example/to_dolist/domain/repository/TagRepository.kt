package com.example.to_dolist.domain.repository

import com.example.to_dolist.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    fun getAllTag(): Flow<List<Tag>>

    suspend fun addTag(tag: Tag)

    suspend fun deleteTag(tag: Tag)

    suspend fun updateTag(tag: Tag)
}