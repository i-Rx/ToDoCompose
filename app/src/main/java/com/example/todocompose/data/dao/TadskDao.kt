package com.example.todocompose.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.data.entity.Task
import com.example.todocompose.data.entity.TaskWithTagLists
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Upsert
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Upsert
    suspend fun upsertTag(tag: Tags)

    @Delete
    suspend fun deleteTag(tag: Tags)

    @Query("SELECT * FROM tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Query("Select * FROM tags_table WHERE tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<List<TaskWithTagLists>>

    @Query("Select * FROM task_table WHERE date LIKE :date")
     fun sortByCreationDate(date: String): Flow<List<Task>>

     @Upsert
     suspend fun upsertTagList(tag: List<Tags>)



}