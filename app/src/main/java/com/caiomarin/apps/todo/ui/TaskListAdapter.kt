package com.caiomarin.apps.todo.ui

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caiomarin.apps.todo.R
import com.caiomarin.apps.todo.databinding.ItemTaskBinding
import com.caiomarin.apps.todo.model.Task
import org.jetbrains.anko.find
import org.w3c.dom.Text

class TaskListAdapter(
    private val tasks: List<Task>,
    private val context: Context,
    var quandoItemClicado: (task: Task) -> Unit = {}): RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    fun getTask(position: Int) : Task {
        return tasks[position]
    }

    inner class TaskListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val title = itemView.findViewById<TextView>(R.id.txtTitle)
        val dateHour = itemView.findViewById<TextView>(R.id.txtDateHour)
        //val image = itemView.find<CardView>(R.id.iv_more)

        fun bind(task: Task){
            title.text = task.title
            dateHour.text = task.date + " - " + task.hour
            itemView.setOnClickListener{
                quandoItemClicado(task)
            }
        }
    }

}

