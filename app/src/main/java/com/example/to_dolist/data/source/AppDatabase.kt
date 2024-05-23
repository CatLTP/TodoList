package com.example.to_dolist.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_dolist.helpers.Converters
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.domain.model.Task

@Database(
    entities = [Task::class, Tag::class],
    version = 5,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun tagDao(): TagDao
}