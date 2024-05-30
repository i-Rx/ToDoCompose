package com.example.todocompose.data.repository

import androidx.room.Query
import com.example.todocompose.data.dao.TaskDao
import com.example.todocompose.data.entity.SearchResults
import com.example.todocompose.data.entity.TagWithTaskLists
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.data.entity.Task
import com.example.todocompose.data.entity.TaskTagCrossRef
import com.example.todocompose.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    suspend fun insertTask(task: Task): Long {
        return taskDao.addTask(task)
    }

    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>) {
        taskDao.insertTaskTagCrossRefs(taskTagCrossRefs)
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

    fun getTagWithTasksList(tagName: String): Flow<TagWithTaskLists> {
        return taskDao.getTagsWithTask(tagName)
    }

    fun getAllTags(): Flow<List<Tags>> {
        return taskDao.getAllTags()
    }

    suspend fun insertTagList(tagList: List<Tags>) {
        return taskDao.upsertTagList(tagList)
    }

    fun sortTasksByDate(date: String): Flow<List<TaskWithTags>> {
        return taskDao.sortByCreationDate(date)
    }

    fun getTagWithTaskLists() = taskDao.getTagWithTaskLists()

    suspend fun searchCombined(searchQuery: String): SearchResults{
        return taskDao.searchCombined(searchQuery)
    }

    suspend fun getTaskWithTagsById(taskId:Long) = taskDao.getTaskWithTagsById(taskId)

    fun getAllTaskWithTags() = taskDao.getAllTaskWithTags()

    suspend fun updateTaskWithTags(task :Task , tags: List<Tags>){
        taskDao.updateTaskWithTags(task,tags)
    }

    suspend fun getAllTasksWithTags(): List<TaskWithTags>{
        return taskDao.getAllTasksWithTags()
    }
}