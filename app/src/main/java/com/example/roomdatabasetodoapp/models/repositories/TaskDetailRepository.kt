package com.example.roomdatabasetodoapp.models.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.roomdatabasetodoapp.models.db.Task
import com.example.roomdatabasetodoapp.models.db.TaskDatabase

class TaskDetailRepository(application: Application) {
    private val taskDetailDao = TaskDatabase.getDatabase(application).taskDetailDao()

    fun getTask(id: Long): LiveData<Task> {
        return taskDetailDao.getTask(id)
    }

    suspend fun insertTask(task: Task): Long {
        return taskDetailDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDetailDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        return taskDetailDao.deleteTask(task)
    }


}