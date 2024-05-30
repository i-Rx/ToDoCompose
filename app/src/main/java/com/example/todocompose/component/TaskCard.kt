package com.example.todocompose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.todocompose.data.entity.Tags
import com.example.todocompose.ui.theme.Navy
import com.example.todocompose.ui.theme.Pink80
import com.example.todocompose.ui.theme.PrimaryColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskCard(
    taskTitle: String,
    timeFrom: String?,
    timeTo: String?,
    tag: List<Tags?>?,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val dividerHeight = remember { mutableStateOf(50.dp) }
    val showDropdown = rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                tag?.firstOrNull()?.color?.toIntOrNull() ?: PrimaryColor.toArgb()
            ).copy(0.1f)
        ),
        onClick = {
            onClick.invoke()
        }
    ) {
        Column() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp), Arrangement.SpaceBetween
            ) {
                Row {
                    //divider
                    Box(
                        modifier = Modifier
                            .height(dividerHeight.value)
                            .width(3.dp)
                            .background(
                                Color(
                                    tag?.firstOrNull()?.color?.toIntOrNull()
                                        ?: PrimaryColor.toArgb()
                                ),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(0.dp, 40.dp)
                    )
                    Column(modifier = Modifier
                        .padding(4.dp)
                        .onGloballyPositioned {
                            dividerHeight.value = it.size.height.dp / 2
                        }) {
                        Text(
                            text = taskTitle,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy
                        )
                        Text(text = "$timeFrom - $timeTo", fontSize = 15.sp, color = Color.Gray)
                    }
                }
                Box {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showDropdown.value = true
                            })
                    DropDownMenu(showDropdown, onEdit = {
                        onClick.invoke()
                        showDropdown.value = false
                    }, onDelete = {
                        onDelete.invoke()
                        showDropdown.value = false
                    })
                }
            }
            FlowRow(
                Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 10.dp), Arrangement.spacedBy(10.dp)
            ) {
                tag?.forEach { tag ->
                    Box(
                        Modifier
                            .background(
                                Color(
                                    tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()
                                ).copy(0.4f),
                                RoundedCornerShape(16.dp)
                            )
                    ) {
                        Text(
                            text = tag?.name.orEmpty(),
                            modifier = Modifier.padding(5.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
//to convert color to String and vice versa
//    val color=Color.Gray.toArgb().toString()
//    Color(color.toIntOrNull()?: PrimaryColor.toArgb())
}
@Composable
fun DropDownMenu(
    showDropdown: MutableState<Boolean>,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    DropdownMenu(
        modifier = Modifier.padding(12.dp),
        expanded = showDropdown.value,
        onDismissRequest = { showDropdown.value = false })
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onEdit.invoke()
            }) {
            Icon(Icons.Outlined.Edit, contentDescription = "")
            Text(text = "Edit", modifier = Modifier.padding(12.dp))
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onDelete.invoke()
            }) {
            Icon(Icons.Outlined.Delete, contentDescription = "")
            Text(text = "Delete", modifier = Modifier.padding(12.dp))
        }
    }
}

//
//@Composable
//fun DropDownMenu(
//    itemList: List<String>,
//    selectedIndex: Int,
//    modifier: Modifier,
//    onItemClick: (Int) -> Unit
//) {
//    var showDropDown by rememberSaveable { mutableStateOf(true) }
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Box() {
//            if (showDropDown) {
//                Popup(
//                    Alignment.TopCenter,
//                    properties = PopupProperties(
//                        excludeFromSystemGesture = true
//                    ),
//                    onDismissRequest = { showDropDown = false }
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .heightIn(max = 90.dp)
//                            .verticalScroll(state = scrollState)
//                            .border(width = 1.dp, color = Color.Gray),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        itemList.onEachIndexed { index, item ->
//                            if (index != 0) {
//                                Divider(thickness = 1.dp, color = Color.LightGray)
//                            }
//                            Box(
//                                modifier = Modifier
//                                    .background(Color.White)
//                                    .fillMaxWidth()
//                                    .clickable {
//                                        onItemClick(index)
//                                        showDropDown = !showDropDown
//                                    },
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(text = item)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
