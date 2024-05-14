package com.example.todocompose.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.data.entity.Task
import com.example.todocompose.data.entity.TaskType
import com.example.todocompose.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasks = taskRepository.getAllTasks()
    val tags = taskRepository.getAllTags()
    val taskByTags = taskRepository.getTaskWithTags("Personal")

    init {
        viewModelScope.launch {
            taskRepository.insertTag(
                Tags(
                    "Work",
                    "color"
                )
            )
            taskRepository.insertTag(
                Tags(
                    "Personal",
                    "color"
                )
            )
            taskRepository.insertTask(
                Task(
                    title = "title",
                    description = "description",
                    date = "2024-01-01",
                    taskType = TaskType.Ongoing.type,
                    timeFrom = "4:00",
                    timeTo = "5:00",
                    tagName = "Work"
                )
            )
            taskRepository.insertTask(
                Task(
                    title = "title2",
                    description = "description",
                    date = "2024-05-05",
                    taskType = TaskType.Ongoing.type,
                    timeFrom = "10:00",
                    timeTo = "11:00",
                    tagName = "Personal"
                )
            )
        }
    }
}