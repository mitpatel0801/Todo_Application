package com.example.roomdatabasetodoapp.ui.viewModels

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.roomdatabasetodoapp.models.db.SortColumn
import com.example.roomdatabasetodoapp.models.db.Task
import com.example.roomdatabasetodoapp.models.repositories.TaskListRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = TaskListRepository(application)

    private val _sortOrder = MutableLiveData(SortColumn.Priority)

    fun changeSortOrder(sortOrder: SortColumn) {
        _sortOrder.value = sortOrder
    }

    val tasks:LiveData<List<Task>> = Transformations.switchMap(_sortOrder){
        repo.getTasks(it)
    }
}