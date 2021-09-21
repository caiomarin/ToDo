package com.caiomarin.apps.todo.ui

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caiomarin.apps.todo.R
import com.caiomarin.apps.todo.database.TaskDatabase
import com.caiomarin.apps.todo.model.Task
import com.caiomarin.apps.todo.ui.AddTaskActivity.Companion.TASK_ID
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    private lateinit var recycleView: RecyclerView
    private lateinit var btnNewTask: FloatingActionButton
    private lateinit var emptyState: View
    private lateinit var adapter: TaskListAdapter

    private lateinit var model: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider(this ).get(TaskViewModel::class.java)
        emptyState = findViewById(R.id.include_empty)

        setupRecycleView()
        insertListeners()
    }

    private fun setupRecycleView() {
        recycleView = findViewById(R.id.rvTasks)

        recycleView.layoutManager = LinearLayoutManager(this)
        model.allTasks.observe(this, Observer {
            adapter = TaskListAdapter(it, this) { abreVisualizadorTask(it) }
            emptyState.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            recycleView.adapter = adapter

            //val touchHelper = ItemTouchHelper(TouchHelper(adapter, this))
            //touchHelper.attachToRecyclerView(recycleView)
        })


    }

    private fun abreVisualizadorTask(it: Task) {
        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra(TASK_ID, it.id)
        startActivity(intent)
    }

    private fun insertListeners() {
        btnNewTask = findViewById(R.id.btnNewTask)
        btnNewTask.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

}