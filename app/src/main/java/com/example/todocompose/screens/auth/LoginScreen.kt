package com.example.todocompose.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todocompose.component.GoogleSignIn
import com.example.todocompose.navigation.Screen
import com.example.todocompose.ui.theme.PrimaryColor

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
//            .background(Color.LightGray)
            .padding(0.dp, 40.dp, 0.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(40.dp, 50.dp),
            color = PrimaryColor,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            text = "Login"
        )
        val textState = remember {
            mutableStateOf(
                mapOf(
                    "username" to "",
                    "email" to "",
                    "password" to ""
                )
            )
        }
        TextField(
            value = textState.value["username"] ?: "",
            onValueChange = { newText ->
                textState.value += ("username" to newText)
            },
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(20.dp),
            placeholder = {
                Text(text = "Email ID or Username", color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Username", tint = Color.Gray)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Gray,
            )
        )
        TextField(
            value = textState.value["password"] ?: "",
            onValueChange = { newText ->
                textState.value += ("password" to newText)
            },
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(20.dp),
            placeholder = {
                Text(text = "Password", color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password", tint = Color.Gray)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Gray,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(.9f),
            Arrangement.End,
            Alignment.CenterVertically
        ) {
            Text(
                text = "Forgot Password ?",
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.padding(30.dp))

        Button(
            onClick = { navController.navigate(Screen.Authenticaion.Login.route) },
            Modifier
                .fillMaxWidth(.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(PrimaryColor),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Login", fontSize = 20.sp)
        }
        Row(
            modifier = Modifier
                .padding(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                Modifier
                    .weight(2f)
                    .height(2.dp))
            Text(
                text = "or with",
                Modifier.padding(8.dp),
                color = Color.Gray,
                fontSize = 14.sp
            )
            Divider(
                Modifier
                    .weight(2f)
                    .height(2.dp))
        }

        GoogleSignIn(authViewModel = authViewModel)

        Column(
            modifier = Modifier.fillMaxSize(1f)
                .padding(45.dp),
            Arrangement.Bottom,
            Alignment.CenterHorizontally
        ){
            Row() {
                Text(
                    text = "Don’t have an account? ",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Authenticaion.SignUp.route)
                    },
                    text = "Sign up ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    }
