package com.example.uptodo.components.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.uptodo.components.CommonDialog
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.InputField
import com.example.uptodo.navigation.Graph
import com.example.uptodo.screens.category.Icons
import com.example.uptodo.screens.category.Priority
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.screens.profile.ProfileViewModel
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40

@Composable
fun CategoryDialog(
    state: MutableState<Boolean>,
    btnText: String,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    CommonDialog(state = state) {
        BodyContent(
            navController = navController,
            btnText = btnText,
            dialogState = state,
            viewModel = viewModel
        ) {}
    }
}

@Composable
private fun BodyContent(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    btnText: String,
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int) -> Unit,
) {
    val selectedIndex = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose Category", textAlign = TextAlign.Center, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        )
        {
            itemsIndexed(Icons.values()) { index, icons ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .background(icons.color)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = {
                                dialogState.value = false
                                selectedIndex.value = index
                                onItemSelection(selectedIndex.value)
                                viewModel.onIconChange(icons)
                            },
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = icons.icon),
                                contentDescription = "icons",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                    Text(
                        text = icons.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    navController.navigate(Graph.DETAILS)
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = btnText)
            }
        }

    }
}

@Composable
fun PriorityDialog(state: MutableState<Boolean>, viewModel: HomeViewModel = hiltViewModel()) {
    CommonDialog(state = state) {
        PriorityContent(
            dialogState = state,
            viewModel = viewModel,
            onItemSelection = {}
        )
    }
}

@Composable
private fun PriorityContent(
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val dialogContent: List<Priority>
    dialogContent = ArrayList()
    val selectedIndex = remember {
        mutableStateOf(0)
    }
    for (i in dialogContent) {
        i.icon
        i.value.toString()
    }

    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Task Priority", textAlign = TextAlign.Center, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(Priority.values()) { index, priority ->
                Column {
                    Box(
                        modifier = Modifier
                            .size(width = 40.dp, height = 50.dp)
                            .background(if (selectedIndex.value == index) Purple40 else Color.Black)
                            .clip(shape = RoundedCornerShape(10.dp)).padding(5.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        IconButton(
                            modifier = Modifier.size(25.dp),
                            onClick = {
                                selectedIndex.value = index
                                onItemSelection(selectedIndex.value)
                                viewModel.onPriorityChange(priority)

                            },
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = priority.icon),
                                contentDescription = "icons",
                                tint = Color.White,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        Text(
                            text = priority.value.toString(),
                            color = Color.White,
                            modifier = Modifier.padding(top = 25.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BottomBarColor,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(width = 150.dp, height = 40.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun LibraryIcon(
    state: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel(),
    selectedItem: (Int) -> Unit
) {
    CommonDialog(state = state) {
        IconLibraryContent(
            dialogState = state,
            viewModel = viewModel,
            onItemSelection = { selectedItem(it) }
        )
    }
}

@Composable
private fun IconLibraryContent(
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val selectedIndex = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose Icon From Library", textAlign = TextAlign.Center, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            itemsIndexed(Icons.values()) { index, icons ->
                Column {
                    IconButton(
                        modifier = Modifier.size(35.dp),
                        onClick = {
                            selectedIndex.value = index
                            onItemSelection(selectedIndex.value)
                            dialogState.value = false
                        },
                    ) {
                        DrawableIcon(
                            painter = painterResource(id = icons.icon),
                            contentDescription = "icons",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccountNameDialog(dialogState: MutableState<Boolean>) {
    CommonDialog(state = dialogState) {
        ChangeAccountName(dialogState = dialogState)
    }
}

@Composable
private fun ChangeAccountName(
    dialogState: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Change account name", textAlign = TextAlign.Center, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))

        InputField(placeholderText = "Account Name", onFieldChange = {})
        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BottomBarColor,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Edit")
            }
        }
    }
}

@Composable
fun ChangePasswordDialog(
    dialogState: MutableState<Boolean>,
) {
    CommonDialog(state = dialogState) {
        ChangeAccountPassword(dialogState = dialogState)
    }
}

@Composable
private fun ChangeAccountPassword(
    dialogState: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Change account Password", textAlign = TextAlign.Center, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BottomBarColor)
            .padding(20.dp)
    ) {

        InputField(
            placeholderText = "Enter old Password",
            onFieldChange = {},
            label = "Enter old Password",
            isFieldSecured = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        InputField(
            placeholderText = "Enter new Password",
            onFieldChange = {},
            label = "Enter new Password",
            isFieldSecured = true
        )

        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BottomBarColor,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Edit")
            }
        }
    }
}

@Composable
fun LogoutDialog(dialogState: MutableState<Boolean>,navigate: (String) -> Unit) {
    CommonDialog(state = dialogState) {
        Logout(dialogState = dialogState, navigate = navigate)
    }
}

@Composable
private fun Logout(
    dialogState: MutableState<Boolean>,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigate : (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Are you sure you want to logout", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BottomBarColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Cancel", color = Purple40)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.logout(navigate)
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp)

            ) {
                Text(text = "Logout")
            }

        }
    }
}

@Composable
fun DeleteTaskDialog(state: MutableState<Boolean>) {
    CommonDialog(state = state) {
        DeleteTaskContent(state = state)
    }
}

@Composable
fun DeleteTaskContent(
    state: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val todo by viewModel.todo
    Column(
        modifier = Modifier
            .background(BottomBarColor)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Delete Task", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Are you sure you want to delete this task?",
                color = Color.White,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Task title: ${todo.title}", color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { state.value = false }, colors = ButtonDefaults.buttonColors(
                        containerColor = BottomBarColor,
                        contentColor = Purple40
                    )
                ) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.height(15.dp))

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.onDelete(todo)
                        state.value = false
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Delete")
                }

            }
        }
    }
}

