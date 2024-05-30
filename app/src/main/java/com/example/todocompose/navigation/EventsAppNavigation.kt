package com.example.todocompose.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.todocompose.component.MonthCalendarView
import com.example.todocompose.screens.task.HomeScreen
import com.example.todocompose.screens.auth.AuthViewModel
import com.example.todocompose.screens.auth.LoginScreen
import com.example.todocompose.screens.auth.SingUpScreen
import com.example.todocompose.screens.auth.SpalshScreen
import com.example.todocompose.screens.task.AddTagDialog
import com.example.todocompose.screens.task.AddTaskScreen
import com.example.todocompose.screens.task.CategoryScreen
import com.example.todocompose.screens.task.SettingsScreen
import com.example.todocompose.screens.task.TaskByDateScreen
import com.example.todocompose.screens.task.TaskViewModel
import com.example.todocompose.screens.task.TasksByCategory
import com.example.todocompose.screens.task.UpdateTaskScreen
import com.google.firebase.auth.FirebaseUser
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random


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
            authViewModel.logout(context)
        }) {
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
        route = Screen.Authenticaion.route,
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
) {
    navigation(
        startDestination = Screen.MainApp.Home.route,
        route = Screen.MainApp.route,
    ) {
        composable(Screen.MainApp.Home.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            HomeScreen(userName.invoke(), navController, viewModel)
        }

        composable(Screen.MainApp.TaskByDate.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            TaskByDateScreen(viewmodel, navController)
        }

        composable(Screen.MainApp.CategoryScreen.route) {
            val taskViewModel: TaskViewModel = hiltViewModel()
            CategoryScreen(userName.invoke(), taskViewModel, navController, logout)
        }

        composable(Screen.MainApp.AddScreen.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
                     viewmodel.taskDate.value = it.savedStateHandle.get<String>("selectedDate").orEmpty()
            AddTaskScreen(navController, viewmodel)
        }

        composable(Screen.MainApp.StaticsScreen.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    val modelProducer = remember { CartesianChartModelProducer.build() }
                    val x = (1..50).toList()
                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.Default) {
                            modelProducer.tryRunTransaction {
                                lineSeries {
                                    series(x, x.map { Random.nextFloat() * 15 })
                                }
                            }
                        }
                    }
//                    val tags = viewmodel.allTags.collectAsState()
//                    ComposeChart7(tags.value, modelProducer, Modifier.size(300.dp))
//                    ComposeChart10(Modifier.size(300.dp))
                }
            }
        }
        dialog(
            Screen.MainApp.DateDialog.route, dialogProperties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {

            MonthCalendarView(navController) {
                navController.popBackStack()
            }
        }
        dialog(
            Screen.MainApp.AddTagDialog.route, dialogProperties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            val addTaskViewModel: TaskViewModel = hiltViewModel()
            AddTagDialog(navController, addTaskViewModel)
        }

        composable("${Screen.MainApp.TaskByCategory.route}/{tagName}", arguments = listOf(
            navArgument("tagName") {
                type = NavType.StringType
            }
        )) { navArgument ->
            val taskViewModel: TaskViewModel = hiltViewModel()
            TasksByCategory(
                navController,
                taskViewModel,
                navArgument.arguments?.getString("tagName")
            )
        }

        composable(
            "${Screen.MainApp.UpdateTask.route}/{taskId}", arguments =
            listOf(navArgument("taskId") {
                type = NavType.LongType
            })
        ) {
            val viewmodel: TaskViewModel = hiltViewModel()
             UpdateTaskScreen(navController, viewmodel, it.arguments?.getLong("taskId"), it)
        }

        composable(Screen.MainApp.Settings.route) {
             SettingsScreen(navController)
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}