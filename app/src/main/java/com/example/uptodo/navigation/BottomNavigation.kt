package com.example.uptodo.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.uptodo.R
import com.example.uptodo.components.DateAndTimePicker
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.components.categories.CategoryDialog
import com.example.uptodo.components.categories.PriorityDialog
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.BottomBarColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val pages = listOf(
        BottomBar.Home,
        BottomBar.Profile,
        BottomBar.Calender,
        BottomBar.Focus
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val bottomBarDestination = pages.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(backgroundColor = BottomBarColor) {
            pages.forEach { screen ->
                AddItems(
                    screen = screen,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    sheetValue: ModalBottomSheetState,
) {

    val sheetContentState = remember {
        mutableStateOf(0)
    }
    ModalBottomSheetLayout(
        sheetState = sheetValue,
        sheetContent = {
            when (sheetContentState.value) {
                0 -> {
                    BottomScreen1(sheetValue = sheetValue, navController = navController)
                }
                1 -> {
                    BottomScreen2(state = sheetValue)
                }
            }

        },
        sheetBackgroundColor = BottomBarColor,
        sheetShape = RoundedCornerShape(20.dp),
    ) {}
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomScreen1(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    sheetValue: ModalBottomSheetState,
) {
    val coroutineScope = rememberCoroutineScope()

    val todo by viewModel.todo
    val context = LocalContext.current
    val categoryState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val priorityState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val dateState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BottomBarColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            androidx.compose.material.Text(text = "Add Task", color = Color.White)

            InputField(
                value = todo.title,
                placeholderText = "Add Title",
                onFieldChange = viewModel::onTitleChange,
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                value = todo.description,
                placeholderText = "Description",
                onFieldChange = viewModel::onDescriptionChange,
            )
            if (dateState.value) {
                DateAndTimePicker(state = dateState, viewModel, viewModel::onDateChange)
            }
            Row {
                IconButton(onClick = {
                    dateState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.timer),
                        contentDescription = "Timer",
                        tint = Color.White,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

                if (categoryState.value) {
                    CategoryDialog(
                        btnText = "Add Category",
                        navController = navController,
                        state = categoryState
                    )
                }
                IconButton(onClick = {
                    categoryState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.tag),
                        contentDescription = "Tag",
                        tint = Color.White,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

                if (priorityState.value) {
                    PriorityDialog(
                        state = priorityState
                    )
                }
                IconButton(onClick = {
                    priorityState.value = true
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.priority),
                        contentDescription = "priority",
                        tint = Color.White,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    coroutineScope.launch {
                        viewModel.onSaveClick()
                        Toast
                            .makeText(
                                context,
                                "Added succesfully",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        sheetValue.hide()
                    }
                }) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.save),
                        contentDescription = "save",
                        modifier = Modifier
                            .size(25.dp),
                        tint = Color.Unspecified
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomScreen2(
    viewModel: HomeViewModel = hiltViewModel(),
    state: ModalBottomSheetState
) {
    val todo by viewModel.todo
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        )
        {
            Row {
                Box(
                    modifier = Modifier
                        .size(width = 30.dp, height = 30.dp)
                        .background(Color.Black)
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "close",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            scope.launch {
                                state.hide()
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        androidx.compose.material.Text(
                            text = todo.title,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        DrawableIcon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "edit",
                            tint = Color.White,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Row {
                        androidx.compose.material.Text(
                            text = todo.description,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "time",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                androidx.compose.material.Text(text = "Task Time:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    androidx.compose.material.Text(
                        text = viewModel.getDateAndTime(todo),
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.tag),
                    contentDescription = "category",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                androidx.compose.material.Text(text = "Task Category:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    DrawableIcon(
                        painter = painterResource(id = todo.icon?.icon!!),
                        contentDescription = "icon"
                    )
                    androidx.compose.material.Text(
                        text = todo.icon?.title!!,
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.priority),
                    contentDescription = "priority",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                androidx.compose.material.Text(text = "Task Priority:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    androidx.compose.material.Text(
                        text = todo.priority?.value.toString(),
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.subtask),
                    contentDescription = "subtask",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                androidx.compose.material.Text(text = "Sub-Task:")

                Spacer(modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 30.dp)
                        .background(BottomBarColor)
                ) {
                    androidx.compose.material.Text(
                        text = "Add Sub-Task",
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
            Row {
                DrawableIcon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "delete",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 4.dp)
                )
                androidx.compose.material.Text(text = "Delete Task", color = Color.Red)

            }

        }
    }
}


@Composable
fun RowScope.AddItems(
    screen: BottomBar,
    navController: NavHostController,
    currentDestination: NavDestination?,

    ) {
    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }

        },
        label = {
            Text(text = screen.title, color = Color.White, fontSize = 12.sp)
        },
        icon = {
            DrawableIcon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.title,
                modifier = Modifier.size(25.dp),
                tint = Color.White
            )
        },
        selectedContentColor = Color.White,
        unselectedContentColor = Color.Black
    )
}





