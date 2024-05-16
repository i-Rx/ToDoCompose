package com.example.todocompose.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todocompose.component.AddTagsListView
import com.example.todocompose.component.CustomTextField
import com.example.todocompose.component.TaskHearderView
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.navigation.Screen
import com.example.todocompose.ui.theme.PrimaryColor


@Composable
fun AddTaskScreen(
    navController: NavHostController,
    addTask: AddTaskViewModel
) {
    val list = listOf(
        Tags("Home", Color.Red.toArgb().toString()),//
        Tags("Work", Color.Green.toArgb().toString()),
        Tags("School", Color.Blue.toArgb().toString()),
        Tags("Personal", Color.Yellow.toArgb().toString())
    )
    LaunchedEffect(Unit) {
        addTask.addTags(list)
    }

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {

        item {
            TaskHearderView("Add Task")
        }
        item {
            CustomTextField(Modifier, "Title", Color.Gray, addTask.title)
            CustomTextField(Modifier, "Date", Color.Gray, addTask.taskDate, trailingIcon = {
                Icon(imageVector = Icons.Outlined.DateRange, "", modifier =
                Modifier.clickable {
                    navController.navigate(Screen.MainApp.DateDialog.route)
                })
            })

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomTextField(Modifier.weight(1f), "Time From", Color.Gray, addTask.startTime)
                CustomTextField(Modifier.weight(1f), "Time To", Color.Gray, addTask.endTime)
            }
            CustomTextField(Modifier, "Description", Color.Gray, addTask.decsription)

        }
        //tags List
        item {
            AddTagsListView(list) {
                addTask.category.value = it.name
            }
        }

        item {
            //add task button
            ButtonAddTask(addTask)
        }
    }
}


@Composable
fun ButtonAddTask(addTask: AddTaskViewModel) {
    Button(
        onClick = {
            addTask.addTask()
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
            .padding(bottom = 100.dp)
            .semantics {
                testTag = "Add Task Button"
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp), text = "Create",
            fontSize = 16.sp,
            color = Color.White
        )
    }
}