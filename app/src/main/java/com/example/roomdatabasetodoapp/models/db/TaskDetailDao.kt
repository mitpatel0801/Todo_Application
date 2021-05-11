package com.example.roomdatabasetodoapp.models.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDetailDao {

    @Query("SELECT * FROM Task Where id = :id ")
    fun getTask(id: Long): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task):Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

}