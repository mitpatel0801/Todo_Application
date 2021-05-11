package com.example.roomdatabasetodoapp.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class SortColumn {
    Title, Priority
}

enum class PriorityLevel {
    Low, Medium, High
}

enum class TaskStatus {
    Open, Close
}


@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val detail: String,
    val priority: Int,
    val status: Int
)
