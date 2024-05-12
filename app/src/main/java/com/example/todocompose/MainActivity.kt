package com.example.todocompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.todocompose.component.BottomBar
import com.example.todocompose.navigation.NavGraph
import com.example.todocompose.navigation.Screen
import com.example.todocompose.screens.auth.AuthViewModel
import com.example.todocompose.screens.auth.SingUpScreen
import com.example.todocompose.ui.theme.ToDoComposeTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val authViewModel : AuthViewModel = hiltViewModel()
            ToDoComposeTheme {

                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(false) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar =when(navBackStackEntry?.destination?.route) {
                        Screen.MainApp.Home.route -> true
                        Screen.MainApp.AddScreen.route -> true
                        Screen.MainApp.TaskByDate.route -> true
                        Screen.MainApp.CategorrScreen.route -> true
                        Screen.MainApp.StaticsScreen.route -> true
                        else -> false
                }
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            contentDescription = "My Screen"
                        },
                ){
                    paddingValues ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)){

                        if (authViewModel.error.value.isNotEmpty()){
                            Snackbar(modifier =
                            Modifier.padding(16.dp)
                                .fillMaxWidth(),
                                containerColor = Color.Red.copy(0.5f)
                            ){
                                Text(text = authViewModel.error.value)
                            }

                        }
                        NavGraph(
                            authViewModel = authViewModel,
                            navController =navController)
                    }
                    if (showBottomBar){
                        BottomBar(navController)
                    }
                }
            }
        }
    }
}


@Composable
fun GoogleSignIn(authViewModel: AuthViewModel){
    Column(
        modifier = Modifier
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var user by remember {
            mutableStateOf(Firebase.auth.currentUser)
        }
        val launcher = rememberFirebaseAuthLauncher(
            onAuthComplete = { result ->
                user = result.user
            },
            onAuthError = {
                user = null
                println()
            })
        val token =
            "286534524044-jug96mog9sjidekdllaedlrpd87tuhku.apps.googleusercontent.com"
        val context = LocalContext.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (user == null) {
                authViewModel.isSigndIn.value = Screen.MainApp.route
              //  Text(text = "Not Logged in")
                Button(onClick = {
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, contentColor = Color.Black),
                    shape = RoundedCornerShape(100f),
                    modifier = Modifier.size(70.dp)
                    ) {
//                    Text(text = " Sign in via Google ")
                    Image(painter = painterResource(id = R.drawable.google), contentDescription = null)
                }
            } else {

                authViewModel.isSigndIn.value = Screen.MainApp.route
//
//                Text(text = "Welcome ${user?.displayName}")
//                AsyncImage(
//                    model = user?.photoUrl,
//                    contentDescription = null,
//                    Modifier
//                        .clip(CircleShape)
//                        .size(45.dp)
//                )
//                Button(
//                    onClick = {
//                        Firebase.auth.signOut()
//                        user = null
//                    }) {
//                    Text(text = "Sign Out")
//
//                }
            }
        }
    }
}


    @Composable
    fun rememberFirebaseAuthLauncher(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (ApiException) -> Unit
    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                scope.launch {
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    onAuthComplete(authResult)
                }
            } catch (e: ApiException) {
                onAuthError(e)
            }
        }
    }


