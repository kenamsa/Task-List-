package com.example.tasklist

data class Task(
    val id: Int,
    val description: String,
    var isCompleted: Boolean = false
)
