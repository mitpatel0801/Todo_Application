package com.example.roomdatabasetodoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabasetodoapp.R
import com.example.roomdatabasetodoapp.models.db.PriorityLevel
import com.example.roomdatabasetodoapp.models.db.Task
import com.example.roomdatabasetodoapp.models.db.TaskStatus
import com.example.roomdatabasetodoapp.ui.viewModels.TaskDetailViewModel
import kotlinx.android.synthetic.main.fragment_task_detail.*
import kotlinx.android.synthetic.main.fragment_task_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetail : Fragment() {
    private lateinit var viewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val priorities = mutableListOf<String>()
        PriorityLevel.values().forEach { priorities.add(it.name) }
        val arrayAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, priorities)
        task_priority.adapter = arrayAdapter


        task_priority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateTaskPriorityView(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val id = TaskDetailArgs.fromBundle(requireArguments()).taskId
        viewModel.setTaskId(id)

        viewModel.task.observe(viewLifecycleOwner, Observer {
            it?.let { setData(it) }
        })

        save_task.setOnClickListener {
            saveTask()
        }

        delete_task.setOnClickListener {
            deleteTask()
        }

    }

    private fun setData(task: Task) {
        updateTaskPriorityView(task.priority)
        task_detail.setText(task.detail)
        task_title.setText(task.title)
        if (task.status == TaskStatus.Open.ordinal) {
            status_open.isChecked = true
        } else {
            status_closed.isChecked = true
        }
        task_priority.setSelection(task.priority)
    }

    private fun saveTask() {
        val title = task_title.text.toString()
        val detail = task_detail.text.toString()
        val priority = task_priority.selectedItemPosition
        val selectedStatusButton =
            status_group.findViewById<RadioButton>(status_group.checkedRadioButtonId)
        var status = TaskStatus.Open.ordinal
        if (selectedStatusButton.text == TaskStatus.Close.name) {
            status = TaskStatus.Close.ordinal
        }

        val task = Task(viewModel.taskId.value!!, title, detail, priority, status)
        viewModel.saveTask(task)

        requireActivity().onBackPressed()

    }

    private fun deleteTask() {
        viewModel.deleteTask()
        requireActivity().onBackPressed()
    }

    private fun updateTaskPriorityView(position: Int) {
        when (position) {
            PriorityLevel.Low.ordinal -> {
                task_priority_view.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPriorityLow
                    )
                )
            }
            PriorityLevel.Medium.ordinal -> {
                task_priority_view.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPriorityMedium
                    )
                )
            }
            PriorityLevel.High.ordinal -> {
                task_priority_view.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPriorityHigh
                    )
                )
            }

        }
    }
}