package com.example.tasklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklist.ui.theme.TaskListTheme
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskApp()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp(viewModel: TaskViewModel = viewModel()) {
    var newTaskDescription by remember { mutableStateOf("") }
    val tasks = viewModel.taskList

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = newTaskDescription,
            onValueChange = { newTaskDescription = it },
            label = { Text("Task Description") }
        )

        Button(
            onClick = {
                if (newTaskDescription.isNotBlank()) {
                    viewModel.addTask(newTaskDescription)
                    newTaskDescription = "" // Clear the input
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Add Task")
        }

        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, viewModel: TaskViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Conditionally apply text decoration based on completion status
        Text(
            text = task.description,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 20.sp,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                fontWeight = if (task.isCompleted) FontWeight.Bold else FontWeight.Normal
            )
        )

        // Button to toggle task completion
        Button(
            onClick = { viewModel.toggleTaskCompletion(task) },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(if (task.isCompleted) "Undo" else "Complete")
        }

        // Button to delete task
        Button(onClick = { viewModel.deleteTask(task) }) {
            Text("Delete")
        }
    }
}