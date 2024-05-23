package com.example.to_dolist.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.to_dolist.data.source.AppDatabase
import com.example.to_dolist.data.source.TagDao
import com.example.to_dolist.domain.model.Tag
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ) = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "to_do"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideTaskDao(db: AppDatabase) = db.taskDao()

    @Singleton
    @Provides
    fun provideTagDao(db: AppDatabase) = db.tagDao()
}