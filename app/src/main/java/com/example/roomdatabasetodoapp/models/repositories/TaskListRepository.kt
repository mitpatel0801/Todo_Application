package com.example.roomdatabasetodoapp.models.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.roomdatabasetodoapp.models.db.SortColumn
import com.example.roomdatabasetodoapp.models.db.Task
import com.example.roomdatabasetodoapp.models.db.TaskDatabase
import com.example.roomdatabasetodoapp.models.db.TaskStatus

class TaskListRepository(context: Application) {
    private val taskListDao = TaskDatabase.getDatabase(context).taskListDao()

    fun getTasks(sort: SortColumn = SortColumn.Priority): LiveData<List<Task>> {
        return if (sort == SortColumn.Priority) {
            taskListDao.getTaskByPriority(TaskStatus.Open.ordinal)
        } else {
            taskListDao.getTaskByTitle(TaskStatus.Open.ordinal)
        }
    }
}