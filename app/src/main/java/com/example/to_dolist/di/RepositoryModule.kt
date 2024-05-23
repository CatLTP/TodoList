package com.example.to_dolist.di

import com.example.to_dolist.data.source.ImageHandler
import com.example.to_dolist.data.repository.TagRepositoryImpl
import com.example.to_dolist.data.source.TaskDao
import com.example.to_dolist.data.repository.TaskRepositoryImpl
import com.example.to_dolist.data.source.TagDao
import com.example.to_dolist.domain.repository.TagRepository
import com.example.to_dolist.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTaskRepository(taskDao: TaskDao, imageHandler: ImageHandler) : TaskRepository {
        return TaskRepositoryImpl(taskDao, imageHandler)
    }

    @Singleton
    @Provides
    fun provideTagRepository(tagDao: TagDao) : TagRepository {
        return TagRepositoryImpl(tagDao)
    }
}