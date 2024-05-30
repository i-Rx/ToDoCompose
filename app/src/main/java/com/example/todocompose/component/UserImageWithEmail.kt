package com.example.todocompose.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.todocompose.R
import com.example.todocompose.navigation.Screen
import com.example.todocompose.ui.theme.Navy
import com.google.firebase.auth.FirebaseUser

@Composable
fun UserImageWithEmail(user: FirebaseUser? , navController: NavHostController, logout: () -> Unit) {
    val showDropDown = rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(20),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                ),
                onClick = {
                    showDropDown.value = true
                }
            ) {
                Icon(
                    Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                        .padding(16.dp),
                )
                SettingsMenu(showDropDown, settings = {
                    navController.navigate(Screen.MainApp.Settings.route)
                    showDropDown.value = false
                }, logout = {
                    logout.invoke()
                    showDropDown.value = false
                })
            }
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

            if (user?.photoUrl.toString().isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.user_avatar_male),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = user?.photoUrl,
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }


        Text(
            user?.displayName.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Navy
        )
        Text(
            user?.email.orEmpty(),
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun SettingsMenu(showDropDown : MutableState<Boolean>, settings: () -> Unit, logout: () -> Unit){
    DropdownMenu(
        modifier = Modifier.padding(12.dp),
        expanded = showDropDown.value,
        onDismissRequest = {
            showDropDown.value = false
        }) {
        Row (
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                settings.invoke()
            })
        {
            Icon(Icons.Outlined.Settings, contentDescription = "")
            Text(text = "Settings", modifier = Modifier.padding(12.dp))
        }
        HorizontalDivider()
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                logout.invoke()
            })
        {
            Icon(Icons.Outlined.Delete, contentDescription = "")
            Text(text = "Log out", modifier = Modifier.padding(12.dp))
        }
    }
}
