package com.example.uptodo.components.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.uptodo.R
import com.example.uptodo.components.CommonDialog
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.navigation.Graph
import com.example.uptodo.screens.category.Icons
import com.example.uptodo.screens.category.Priority
import com.example.uptodo.screens.home.HomeViewModel
import com.example.uptodo.ui.theme.*

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
fun BodyContent(
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
                    Text(text = icons.title, color = Color.White, fontSize = 12.sp)
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
fun PriorityContent(
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    var dialogContent: List<Priority>
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
                            .clip(RoundedCornerShape(8.dp)),
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
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryIcon(state: MutableState<Boolean>, viewModel: HomeViewModel = hiltViewModel()) {
    CommonDialog(state = state) {
        IconLibraryContent(
            dialogState = state,
            viewModel = viewModel,
            onItemSelection = {}
        )
    }
}

@Composable
fun IconLibraryContent(
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
                        modifier = Modifier.size(25.dp),
                        onClick = {
                            selectedIndex.value = index
                            onItemSelection(selectedIndex.value)
                            viewModel.onIconChange(icons)
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


