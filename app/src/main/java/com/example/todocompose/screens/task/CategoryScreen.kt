package com.example.todocompose.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todocompose.component.TagCard
import com.example.todocompose.component.UserImageWithEmail
import com.example.todocompose.iconByName
import com.example.todocompose.navigation.Screen
import com.example.todocompose.ui.theme.PrimaryColor
import com.google.firebase.auth.FirebaseUser

@Composable
fun CategoryScreen(
    user: FirebaseUser?,
    viewModel: TaskViewModel,
    navController: NavHostController,
    logout: () -> Unit,
) {
    val tagsWithTasksList = viewModel.tagWithTasks

    Column {
        UserImageWithEmail(user = user, navController,logout)
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(tagsWithTasksList.value) {
                TagCard(
                    Color(it.tag.color.toIntOrNull() ?: PrimaryColor.toArgb()),
                    iconByName(it.tag.iconName),
                    it.tag.name,
                    "${it.tasks.size} Task"
                ) {
                    navController.navigate("${Screen.MainApp.TaskByCategory.route}/${it.tag.name}")
                }
            }
        }

    }
}