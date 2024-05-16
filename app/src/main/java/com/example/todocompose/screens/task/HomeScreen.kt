package com.example.todocompose.screens.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todocompose.R
import com.example.todocompose.component.TaskCard
import com.example.todocompose.component.TaskCategoryCard
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.data.entity.TaskType
import com.example.todocompose.ui.theme.Navy
import com.example.todocompose.ui.theme.PrimaryColor
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(invoke: FirebaseUser?) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp)
        .semantics {
            contentDescription = "Home Screen"
        })
    {
        item {
            HeaderView(invoke?.displayName.orEmpty())
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "My Tasks",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Navy
            )
        }
        item {
            Row(
                modifier = Modifier.padding(vertical = 4.dp)
            )
            {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(vertical = 1.dp)
                ) {
                    TaskCategoryCard(
                        TaskType.Completed.type,
                        "7 Tasks",
                        Color(0xFF7DC8E7),
                        height = 220.dp,
                        onClick = {},
                        image = {
                            Image(
                                painter = painterResource(id = R.drawable.imac),
                                contentDescription = "",
                                modifier = Modifier.size(80.dp)
                            )
                        })
                    TaskCategoryCard(
                        TaskType.Canslled.type,
                        "10 Tasks",
                        Color(0xFFE77D7D),
                        height = 190.dp,
                        onClick = { },
                        image = {
                            Icon(
                                imageVector = Icons.TwoTone.Clear,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        })
                }
                Column(
                    modifier = Modifier
                        .weight(.5f)
                        .padding(8.dp)
                ) {
                    TaskCategoryCard(
                        TaskType.Pending.type,
                        "6 Tasks",
                        Color(0xFF7D88E7),
                        height = 190.dp,
                        onClick = { },
                        image = {
                            Icon(
                                painterResource(id = R.drawable.time),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        })
                    TaskCategoryCard(
                        TaskType.Ongoing.type,
                        "15 Tasks",
                        Color(0xFF81E89E),
                        height = 220.dp,
                        onClick = {},
                        image = {
                            Image(
                                painter = painterResource(id = R.drawable.imac),
                                contentDescription = "",
                                modifier = Modifier.size(90.dp)
                            )
                        })
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    "Today Tasks",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Navy
                )
                Text(
                    "View all",
                    modifier = Modifier
                        .padding(top = 8.dp),
                    fontSize = 12.sp,
                    color = PrimaryColor
                )
            }
        }
        items(5){
            TaskCard(
                taskTitle = "Task $it" ,
                timeFrom ="10:00" ,
                timeTo = "11:00",
                tag = Tags(
                    "Work",
                    "Blue"))

            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable
fun HeaderView(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween

    ) {

        Column {
            Text(
                "Hi, $userName",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Navy
            )
            Text(
                "Let's make this day productive",
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }


        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(50),//use 20 if you want to round corners like the one in the design
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),

            ) {
            Image(
                painter = painterResource(id = R.drawable.user_avatar_male),
                contentDescription = "profile picture",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop,


                )
        }
    }
}


//
//    val viewModel: TaskViewModel = hiltViewModel()
//
//    Column(Modifier.fillMaxSize()
//        .padding(0.dp, 40.dp, 0.dp, 0.dp)) {
//        Row (Modifier.
//        fillMaxWidth(1f),
//        Arrangement.SpaceAround){
//            Column {
//                Text( text = "Hi, Waleed",
//                    color = Color.Blue,
//                   fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    fontStyle = FontStyle.Italic)
//                Text(text = "Let’s make this day productive",
//                    color = Color.Black,
//                    fontSize = 14.sp)
//            }
//            Image(modifier = Modifier.size(40.dp),
//                contentScale = ContentScale.Crop,
//                painter = painterResource(id = R.drawable.logo), contentDescription = null)
//        }
//        LazyColumn (
//            modifier = Modifier.fillMaxSize()
//                .padding(12.dp)
//        ){
//            item {
//                val result = viewModel.tasks.collectAsState(null)
//                val tags = viewModel.tags.collectAsState(null)
//                val taskByTag = viewModel.taskByTags.collectAsState(null)
//
//                Text(text = result.value.toString())
//                Spacer(modifier = Modifier.padding(vertical = 12.dp))
//                Text(text = taskByTag.value.toString())
//                Spacer(modifier = Modifier.padding(vertical = 12.dp))
//                Text(text = tags.value.toString())
//            }
//        }
//
//    }
//
//}
