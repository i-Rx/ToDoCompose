package com.example.todocompose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.navigation.Screen
import com.example.todocompose.rememberFirebaseAuthLauncher
import com.example.todocompose.screens.auth.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


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
            }
            }

    }
}
