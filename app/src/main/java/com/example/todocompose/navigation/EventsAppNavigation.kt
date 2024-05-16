package com.example.todocompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.todocompose.screens.task.HomeScreen
import com.example.todocompose.screens.auth.AuthViewModel
import com.example.todocompose.screens.auth.LoginScreen
import com.example.todocompose.screens.auth.SingUpScreen
import com.example.todocompose.screens.auth.SpalshScreen
import com.example.todocompose.screens.task.AddTaskScreen
import com.example.todocompose.screens.task.AddTaskViewModel
import com.example.todocompose.screens.task.TaskByDateScreen
import com.example.todocompose.screens.task.TaskViewModel
import com.google.firebase.auth.FirebaseUser


@Composable
fun EventsAppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = authViewModel.isSignedIn.value,
    ) {
        authNavigation(navController, authViewModel)
        mainAppNavigation(navController, logout = {
            authViewModel.logout(context) }) {
            authViewModel.auth.currentUser
        }
    }
}



fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    navigation(
        startDestination = Screen.Authenticaion.Splash.route,
        route =Screen.Authenticaion.route,
    ) {
        composable(Screen.Authenticaion.Splash.route) {
            SpalshScreen(navController)
        }
        composable(Screen.Authenticaion.SignUp.route) {
            SingUpScreen(navController, authViewModel)
        }

        composable(Screen.Authenticaion.Login.route) {
            LoginScreen(navController, authViewModel)
        }
    }
}


fun NavGraphBuilder.mainAppNavigation(
    navController: NavHostController,
    logout: () -> Unit,
    userName: () -> FirebaseUser?
)  {
    navigation(
        startDestination = Screen.MainApp.Home.route,
        route = Screen.MainApp.route,
    ) {
        composable(Screen.MainApp.Home.route) {
            HomeScreen(userName.invoke())
        }

        composable(Screen.MainApp.TaskByDate.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            TaskByDateScreen(viewmodel)
        }
        composable(Screen.MainApp.CategoryScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                Button(onClick = {
                    logout.invoke()
                }) {
                    Text(text = "SignOut")
                }
            }
        }
        composable(Screen.MainApp.AddScreen.route) {
            val viewmodel: AddTaskViewModel = hiltViewModel()
            viewmodel.taskDate.value = it.savedStateHandle.get<String>("selectedDate").orEmpty()
            AddTaskScreen(navController, viewmodel)
        }

        composable(Screen.MainApp.StaticsScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {

            }
        }
        composable(Screen.MainApp.DateDialog.route) {

       // MonthlyHorizontalCalendarView(navController)
        }

    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}