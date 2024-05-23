package com.example.to_dolist.di

import com.example.to_dolist.helpers.AscendingDateComparision
import com.example.to_dolist.helpers.DescendingDateComparision
import com.example.to_dolist.domain.repository.TagRepository
import com.example.to_dolist.domain.repository.TaskRepository
import com.example.to_dolist.domain.use_case.tag.AddTag
import com.example.to_dolist.domain.use_case.tag.DeleteTag
import com.example.to_dolist.domain.use_case.tag.GetTag
import com.example.to_dolist.domain.use_case.tag.TagUseCase
import com.example.to_dolist.domain.use_case.tag.UpdateTag
import com.example.to_dolist.domain.use_case.task.AddTask
import com.example.to_dolist.domain.use_case.task.DeleteTask
import com.example.to_dolist.domain.use_case.task.DeleteTaskImage
import com.example.to_dolist.domain.use_case.task.GetAllTask
import com.example.to_dolist.domain.use_case.task.GetTask
import com.example.to_dolist.domain.use_case.task.LoadTaskImage
import com.example.to_dolist.domain.use_case.task.SaveTaskImage
import com.example.to_dolist.domain.use_case.task.TasksUseCases
import com.example.to_dolist.domain.use_case.task.UpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTaskUseCases(
        repository: TaskRepository,
        ascendingDateComparision: AscendingDateComparision,
        descendingDateComparision: DescendingDateComparision,
    ): TasksUseCases = TasksUseCases(
        getAllTask = GetAllTask(repository, ascendingDateComparision, descendingDateComparision),
        getTask = GetTask(repository),
        deleteTask = DeleteTask(repository),
        addTask = AddTask(repository),
        updateTask = UpdateTask(repository),
        saveTaskImage = SaveTaskImage(repository),
        loadTaskImage = LoadTaskImage(repository),
        deleteTaskImage = DeleteTaskImage(repository)
    )

    @Provides
    @Singleton
    fun provideTagUseCases(repository: TagRepository): TagUseCase = TagUseCase(
        getTag = GetTag(repository),
        addTag = AddTag(repository),
        deleteTag = DeleteTag(repository),
        updateTag = UpdateTag(repository)
    )
}