package com.caiomarin.apps.todo.ui

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.caiomarin.apps.todo.database.TaskDatabase
import kotlin.concurrent.thread

class TouchHelper(val adapter: TaskListAdapter, val context: Context): ItemTouchHelper.Callback() {

    lateinit var database: TaskDatabase

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    //Não precisamos implementar este método
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean = false

    //Sempre que detectaro Swipe, identificar a anotação na posição e deletar do Banco
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        database = TaskDatabase.getInstance(context)
        thread {
            //adapter.getTask(viewHolder.adapterPosition)
            Log.i("TESTEEEEEEE", viewHolder.adapterPosition.toString())
            Log.i("TESTEEEEEEE", adapter.getItemId(viewHolder.adapterPosition).toString())
            //database.DAO().deleteTask(adapter.getItemId(viewHolder.adapterPosition))

        }
    }


}