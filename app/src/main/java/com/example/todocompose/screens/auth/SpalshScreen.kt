package com.example.todocompose.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todocompose.R
import com.example.todocompose.navigation.Screen
import com.example.todocompose.ui.theme.PrimaryColor


@Composable
fun SpalshScreen(navController: NavHostController) {
    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        Spacer(modifier = Modifier.height(60.dp))
            Image(
                painter = painterResource(id = R.drawable.intro_image),
                contentDescription = ""
            )
        Text(
            text= buildAnnotatedString {
                append("Dailoz")
                withStyle(
                    style =  SpanStyle(
                        color = Color.Red)){
                append(".")
                }
            },
            fontSize = 45.sp,
            color = PrimaryColor,
            fontWeight = FontWeight.ExtraBold)
//            Spacer(modifier = Modifier.padding(10.dp))
            Text( textAlign = TextAlign.Center,
                text = "Plan what you will do to be more organized for today, tomorrow and beyond")

            Spacer(modifier = Modifier.padding(50.dp))

        Button(onClick = { navController.navigate(Screen.Authenticaion.Login.route) },
            Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(PrimaryColor),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(text = "Login", fontSize = 20.sp)
        }
        Box(modifier = Modifier
            .fillMaxWidth(.9f)
            .height(80.dp)
            .padding(22.dp),
            Alignment.Center){
            Text(text = "Sign up",
                Modifier.clickable {
                navController.navigate(Screen.Authenticaion.SignUp.route)
            })
        }


    }
    
    }



