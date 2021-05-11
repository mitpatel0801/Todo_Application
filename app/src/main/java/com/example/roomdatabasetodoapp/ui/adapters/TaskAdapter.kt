package com.example.roomdatabasetodoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasetodoapp.R
import com.example.roomdatabasetodoapp.models.db.PriorityLevel
import com.example.roomdatabasetodoapp.models.db.Task
import kotlinx.android.synthetic.main.list_item.view.*

class TaskAdapter(private val listener: (Long) -> Unit) :
    ListAdapter<Task, TaskAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener(getItem(bindingAdapterPosition).id)
            }
        }

        fun bind(task: Task) {
            itemView.task_title_adapter.text = task.title
            itemView.task_detail_adapter.text = task.detail
            when (task.priority) {
                PriorityLevel.High.ordinal -> {
                    itemView.task_priority_view_adapter.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorPriorityHigh
                        )
                    )
                }
                PriorityLevel.Medium.ordinal -> {
                    itemView.task_priority_view_adapter.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorPriorityMedium
                        )
                    )

                }
                PriorityLevel.Low.ordinal -> {
                    itemView.task_priority_view_adapter.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorPriorityLow
                        )
                    )

                }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallBack : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }


}