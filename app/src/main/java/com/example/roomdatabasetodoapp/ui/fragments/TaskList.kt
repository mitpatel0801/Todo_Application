package com.example.roomdatabasetodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasetodoapp.R
import com.example.roomdatabasetodoapp.models.db.SortColumn
import com.example.roomdatabasetodoapp.ui.adapters.TaskAdapter
import com.example.roomdatabasetodoapp.ui.viewModels.TaskListViewModel
import kotlinx.android.synthetic.main.fragment_task_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [TaskList.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskList : Fragment() {
    private lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(task_list)
        {

            layoutManager = LinearLayoutManager(activity)
            adapter = TaskAdapter {
                findNavController().navigate(TaskListDirections.actionTaskListToTaskDetail(it))
            }
        }


        add_task.setOnClickListener {
            findNavController().navigate(TaskListDirections.actionTaskListToTaskDetail(0))
        }

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            (task_list.adapter as TaskAdapter).submitList(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort_priority -> {
                viewModel.changeSortOrder(SortColumn.Priority)
                item.isChecked = true
                true
            }
            R.id.menu_sort_title -> {
                viewModel.changeSortOrder(SortColumn.Title)
                item.isChecked = true
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }

}