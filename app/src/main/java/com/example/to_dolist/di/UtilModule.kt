package com.example.to_dolist.di

import android.content.Context
import androidx.work.WorkManager
import com.example.to_dolist.helpers.AscendingDateComparision
import com.example.to_dolist.helpers.DescendingDateComparision
import com.example.to_dolist.data.source.ImageHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    @Singleton
    fun provideDateComparision() = AscendingDateComparision()

    @Provides
    @Singleton
    fun provideDescendingDateComparision() = DescendingDateComparision()

    @Provides
    @Singleton
    fun provideImageHandler(
        @ApplicationContext appContext: Context,
    ) = ImageHandler(appContext)

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext appContext: Context,
    ) = WorkManager.getInstance(appContext)
}