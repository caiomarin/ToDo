package com.caiomarin.apps.todo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.caiomarin.apps.todo.database.TaskDatabase
import com.caiomarin.apps.todo.model.Task

class TaskViewModel(application: Application): AndroidViewModel(application) {
    private val db: TaskDatabase = TaskDatabase.getInstance(application)
    internal val allTasks: LiveData<List<Task>> = db.DAO().getAllTasks()

    fun insert(task: Task){
        db.DAO().insertTask(task)
    }

    fun delete(task: Task){
        db.DAO().deleteTask(task)
    }

    fun deleteById(id: Int){
        db.DAO().deleteTaskById(id)
    }

    fun deleteAll(){
        db.DAO().deleteAll()
    }

    fun findById(id: Int) = db.DAO().findById(id)
}