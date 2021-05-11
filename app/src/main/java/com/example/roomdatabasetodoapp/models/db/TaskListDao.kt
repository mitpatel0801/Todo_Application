package com.example.roomdatabasetodoapp.models.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TaskListDao {

    @Query("SELECT * FROM Task Where status=:status ORDER BY priority DESC")
    fun getTaskByPriority(status: Int): LiveData<List<Task>>

    @Query("SELECT * FROM Task  Where status=:status ORDER By title")
    fun getTaskByTitle(status: Int): LiveData<List<Task>>
}