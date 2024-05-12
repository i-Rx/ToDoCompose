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
import kotlin.math.log


@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = authViewModel.isSignedIn.value,
    )
    {
        authNavigations(navController, authViewModel)
        mainNavigation(navController,authViewModel) {
            authViewModel.logout(context)
        }
    }
}

fun NavGraphBuilder.authNavigations(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    navigation(
        startDestination = Screen.Authenticaion.Splash.route,
        route = Screen.Authenticaion.route,
    ) {
        composable(route = Screen.Authenticaion.Splash.route) {
            SpalshScreen(navController)
        }
        composable(route = Screen.Authenticaion.SignUp.route) {
            SingUpScreen(navController,authViewModel)
        }
        composable(route = Screen.Authenticaion.Login.route){
            LoginScreen(navController,authViewModel)
        }
    }
}


fun NavGraphBuilder.mainNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    logout: () -> Unit
) {
    navigation(
        startDestination = Screen.MainApp.Home.route,
        route = Screen.MainApp.route,
    )

    {
        composable(route = Screen.MainApp.Home.route){
          HomeScreen(navController = navController)
        }
    }


    composable(route = Screen.MainApp.TaskByDate.route){
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Yellow)) {}
    }

    composable(route = Screen.MainApp.CategoryScreen.route){
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Red)) {
            Button(onClick = {
                logout.invoke()
            }){
                Text(text = "Sign Out")
            }
        }
    }

    composable(route = Screen.MainApp.AddScreen.route){
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Magenta)) {}
    }

    composable(route = Screen.MainApp.StaticsScreen.route){
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Green)) {}
    }

}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}



