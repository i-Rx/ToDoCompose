package com.example.todocompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todocompose.R
import com.example.todocompose.screens.task.TaskViewModel

@Composable
fun HomeScreen(navController: NavHostController){

    val viewModel: TaskViewModel = hiltViewModel()

    Column(Modifier.fillMaxSize()
        .padding(0.dp, 40.dp, 0.dp, 0.dp)) {
        Row (Modifier.
        fillMaxWidth(1f),
        Arrangement.SpaceAround){
            Column {
                Text( text = "Hi, Waleed",
                    color = Color.Blue,
                   fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic)
                Text(text = "Let’s make this day productive",
                    color = Color.Black,
                    fontSize = 14.sp)
            }
            Image(modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.logo), contentDescription = null)
        }
        LazyColumn (
            modifier = Modifier.fillMaxSize()
                .padding(12.dp)
        ){
            item {
                val result = viewModel.tasks.collectAsState(null)
                val tags = viewModel.tags.collectAsState(null)
                val taskByTag = viewModel.taskByTags.collectAsState(null)

                Text(text = result.value.toString())
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                Text(text = taskByTag.value.toString())
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                Text(text = tags.value.toString())
            }
        }

    }

}
