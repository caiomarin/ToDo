package com.caiomarin.apps.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.caiomarin.apps.todo.model.Task

@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun DAO(): TaskDAO

    companion object {
        private var INSTANCE: TaskDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TaskDatabase{

            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task").build()
            }
            return INSTANCE as TaskDatabase
        }
    }
}