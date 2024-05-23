package com.example.to_dolist.data.repository

import com.example.to_dolist.data.source.TagDao
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow

class TagRepositoryImpl(
    private val tagDao: TagDao
) : TagRepository {

    override fun getAllTag(): Flow<List<Tag>> {
        return tagDao.getAllTag()
    }

    override suspend fun addTag(tag: Tag) {
        return tagDao.addTag(tag)
    }

    override suspend fun deleteTag(tag: Tag) {
        return tagDao.deleteTag(tag)
    }

    override suspend fun updateTag(tag: Tag) {
        return tagDao.updateTag(tag)
    }
}