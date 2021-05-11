package com.example.roomdatabasetodoapp.ui.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.roomdatabasetodoapp.models.db.Task
import com.example.roomdatabasetodoapp.models.repositories.TaskDetailRepository
import kotlinx.coroutines.launch

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = TaskDetailRepository(application)

    private val _taskId: MutableLiveData<Long> = MutableLiveData<Long>(0)

    val taskId: LiveData<Long> get() = _taskId

    val task: LiveData<Task> = Transformations.switchMap(_taskId) {
        repo.getTask(it)
    }

    fun setTaskId(id: Long) {
        if (_taskId.value != id) {
            _taskId.value = id
        }
    }

    fun saveTask(task: Task) {
        viewModelScope.launch {
            if (_taskId.value == 0L) {
                _taskId.value = repo.insertTask(task)
            } else {
                repo.updateTask(task)
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            if (_taskId.value != 0L) {
                task.value?.let { repo.deleteTask(it) }
            }
            }
        }

    }