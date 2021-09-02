package com.caiomarin.apps.todo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caiomarin.apps.todo.database.TaskDatabase
import com.caiomarin.apps.todo.databinding.ActivityMainBinding
import com.caiomarin.apps.todo.model.Task
import com.caiomarin.apps.todo.ui.AddTaskActivity.Companion.TASK_ID
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    lateinit var database: TaskDatabase

    companion object {
        private const val CREATE_NEW_TASK = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatabase()
        setupRecycleView()

        atualizarLista()
        insertListeners()
    }

    private fun atualizarLista() {
        doAsync {
            val result = getTasks()
            uiThread {
                binding.includeEmpty.emptyState.visibility = if (result.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(result)
            }
        }
    }

    private fun getTasks(): List<Task> {
        return database.DAO().getAllTasks()
    }

    private fun resetData() {
        //Fazendo a Query em background e Setando o Adapter novamente
        doAsync {
            val result = getTasks()
            uiThread {
                adapter.submitList(result)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resetData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (resultCode == Activity.RESULT_OK){
           resetData()
       }
    }


    private fun setupRecycleView() {
        binding.rvTasks.adapter = adapter
    }

    private fun setupDatabase() {
        database = TaskDatabase.getInstance(this)
    }

    private fun insertListeners() {
        binding.btnNewTask.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            doAsync {
                database.DAO().deleteTask(it)
                uiThread {
                    resetData()
                }
            }
        }

    }

}