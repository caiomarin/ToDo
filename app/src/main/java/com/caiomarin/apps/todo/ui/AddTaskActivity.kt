package com.caiomarin.apps.todo.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caiomarin.apps.todo.database.TaskDatabase
import com.caiomarin.apps.todo.databinding.ActivityAddTaskBinding
import com.caiomarin.apps.todo.extensions.format
import com.caiomarin.apps.todo.extensions.text
import com.caiomarin.apps.todo.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class AddTaskActivity: AppCompatActivity() {

    companion object {
        const val DATE_PICKER = "DATE_PICKER_TAG"
        const val TIME_PICKER = "TIME_PICKER_TAG"
        const val TASK_ID = "999"
    }

    lateinit var database: TaskDatabase

    private lateinit var binding: ActivityAddTaskBinding
    private var taskI: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatabase()


        if (intent.hasExtra(TASK_ID)) {
            taskI = intent.getIntExtra(TASK_ID, 0)
            doAsync {
                var result = database.DAO().findById(taskI)
                uiThread {
                    binding.edtTitle.text = result.title
                    binding.edtDescription.text = result.description
                    binding.edtDate.text = result.date
                    binding.edtHour.text = result.hour
                }
            }
        }

        insertListeners()

    }

    private fun setupDatabase(){
        database = TaskDatabase.getInstance(this)
    }

    private fun insertListeners() {
        binding.edtDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timezone = TimeZone.getDefault()
                val offset = timezone.getOffset(Date().time) * -1
                binding.edtDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, DATE_PICKER)
        }

        binding.edtHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.edtHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, TIME_PICKER)
        }


        binding.btnCreateTask.setOnClickListener {
            var task = Task(
                    taskI,
                    binding.edtTitle.text,
                    binding.edtDescription.text,
                    binding.edtDate.text,
                    binding.edtHour.text
                )
            doAsync {
                database.DAO().insertTask(task)
                uiThread {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

}