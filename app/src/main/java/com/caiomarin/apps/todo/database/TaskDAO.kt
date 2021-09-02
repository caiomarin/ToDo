package com.caiomarin.apps.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.caiomarin.apps.todo.model.Task

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<Task>

    @Insert(onConflict = REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    fun findById(id: Int): Task
}