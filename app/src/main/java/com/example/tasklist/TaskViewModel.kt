package com.example.tasklist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class TaskViewModel : ViewModel() {
    // Mutable list of tasks
    private val _taskList = mutableStateListOf<Task>()
    val taskList: List<Task> get() = _taskList

    init {
        // Initialize with 100 tasks
        for (i in 1..100) {
            _taskList.add(Task(id = i, description = "Task #$i"))
        }
    }

    // Add new task
    fun addTask(description: String) {
        // Ensure unique ID for each task
        val newTask = Task(id = _taskList.size + 1, description = description)
        _taskList.add(newTask)
    }

    // Delete a task
    fun deleteTask(task: Task) {
        _taskList.remove(task)
    }

    // Toggle task completion status
    fun toggleTaskCompletion(task: Task) {
        // Find the index of the task and update its completion status
        val index = _taskList.indexOf(task)
        if (index != -1) {
            _taskList[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }
}
