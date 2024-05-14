package com.example.todocompose.navigation

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.todocompose.screens.HomeScreen
import com.example.todocompose.screens.auth.AuthViewModel
import com.example.todocompose.screens.auth.LoginScreen
import com.example.todocompose.screens.auth.SingUpScreen
import com.example.todocompose.screens.auth.SpalshScreen
import com.example.todocompose.screens.task.TaskViewModel


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
        mainAppNavigation(navController){
            authViewModel.logout(context)
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
    logout: () -> Unit
) {
    navigation(
        startDestination = Screen.MainApp.Home.route,
        route = Screen.MainApp.route,
    ) {
        composable(Screen.MainApp.Home.route) {
        HomeScreen(navController)
        }


        composable(Screen.MainApp.TaskByDate.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {

            }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Magenta)
            ) {

            }
        }
        composable(Screen.MainApp.StaticsScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {

            }
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}