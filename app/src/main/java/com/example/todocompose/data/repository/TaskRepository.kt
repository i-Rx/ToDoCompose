package com.example.todocompose.data.repository

import com.example.todocompose.data.dao.TaskDao
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.data.entity.Task
import com.example.todocompose.data.entity.TaskWithTagLists
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    suspend fun insertTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    suspend fun insertTag(tag: Tags) {
        taskDao.upsertTag(tag)
    }

    suspend fun deleteTag(tag: Tags) {
        taskDao.deleteTag(tag)
    }

    fun getTaskWithTags(tagName: String): Flow<List<TaskWithTagLists>> {
        return taskDao.getTagsWithTask(tagName)
    }

    fun getAllTags(): Flow<List<Tags>> {
        return taskDao.getAllTags()
    }

    suspend fun insertTagList(tagList: List<Tags>) {
        return taskDao.upsertTagList(tagList)
    }

    fun sortTasksByTag(date: String): Flow<List<Task>> {
        return taskDao.sortByCreationDate(date)
    }
}