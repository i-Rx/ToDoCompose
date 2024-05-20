package com.example.todocompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {

    data object MainApp:Screen("mainGraph") {
        data object Home : Screen(route = "home_screen")
        data object TaskByDate : Screen(route = "task_by_date_screen")
        data object AddScreen : Screen(route = "add_screen")
        data object CategoryScreen : Screen(route = "category_screen")
        data object StaticsScreen : Screen(route = "statics_screen")
        data object DateDialog : Screen(route = "DateDialog")
        data object AddTagDialog: Screen("AddTagDialog")
        data object TaskByCategory: Screen("TaskByCategory")
        data object TimeDialog: Screen("TimeDialog")
    }

    data object Authenticaion : Screen("authGraph"){
        data object Splash : Screen(route = "splash")
        data object SignUp : Screen(route = "signup_route")
        data object Login : Screen(route = "login_route")
    }
}

data class BottomNavigationItem(
    val icon:ImageVector = Icons.Filled.Home,
    val route: String =""
){

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                icon = Icons.Outlined.Home,
                route = Screen.MainApp.Home.route
            ),
            BottomNavigationItem(
                icon = Icons.Outlined.List,
                route = Screen.MainApp.TaskByDate.route
            ),
            BottomNavigationItem(
                icon = Icons.Filled.AddCircle,
                route = Screen.MainApp.AddScreen.route
            ),

            BottomNavigationItem(
                icon = Icons.Outlined.Settings,
                route = Screen.MainApp.CategoryScreen.route
            ),

            BottomNavigationItem(
                icon = Icons.Filled.DateRange,
                route = Screen.MainApp.StaticsScreen.route
            ),
        )
    }
}