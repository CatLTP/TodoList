package com.example.to_dolist.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var title: String,
    @ColumnInfo var tagId: Int?,
    @ColumnInfo var deadline: String? = null,
    @ColumnInfo var timeDeadline: String? = null,
    @ColumnInfo var dateCreate: String,
    @ColumnInfo var isFinished: Boolean = false,
    @ColumnInfo var imageList: List<String>? = null,
    @ColumnInfo var note: String? = null,
)

class InvalidTaskException(message: String): Exception(message)