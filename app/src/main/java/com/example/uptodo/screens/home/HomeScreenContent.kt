package com.example.uptodo.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.DrawerValue
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.SearchField
import com.example.uptodo.components.categories.NavigationDrawerItem
import com.example.uptodo.screens.category.BottomSheetType
import com.example.uptodo.screens.settings.ChangeThemeDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreenContent(
    viewModel: HomeViewModel = hiltViewModel(),
    todoId: String,
    currentBottomSheet : BottomSheetType
    ) {
    val sheetValue = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
    val closeDrawer: () -> Unit = { scope.launch { drawerState.close() } }
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val searchedText = textState.value.text

    val themeDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(viewModel) {
        viewModel.getTodo(todoId)
        viewModel.initailizeTodo()
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (themeDialog.value) {
            ChangeThemeDialog(dialogState = themeDialog, userSetting = viewModel.themeSetting)
        }

        ModalDrawer(
            drawerState = drawerState,
            drawerBackgroundColor = Color.Black,
            drawerContent = {
                val itemsList = navDrawerItems()
                var selectedItem by remember{mutableStateOf( itemsList[0])}

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 24.dp)
                ) {

                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "back",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            closeDrawer()
                        }
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 30.dp))

                    androidx.compose.material.Text(
                        text = "Settings",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    this.items(items = itemsList) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 20.dp)
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = item.icon),
                                contentDescription = "icons",
                                tint = Color.White,
                                modifier = Modifier.size(25.dp)
                            )

                            androidx.compose.material.Text(
                                text = item.title,
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                DrawableIcon(
                                    painter = painterResource(id = item.forwardIcon),
                                    contentDescription = "forward",
                                    tint = Color.White,
                                    modifier = Modifier.clickable {
                                        if (selectedItem == item) {
                                            themeDialog.value = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

            },
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            openDrawer()
                        }
                    }, modifier = Modifier.size(40.dp)) {
                        DrawableIcon(
                            painter = painterResource(id = R.drawable.dropdown),
                            contentDescription = "dropdown",
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                    Text(text = "Home", color = Color.White, textAlign = TextAlign.Center)
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(35.dp),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp, start = 10.dp, end = 10.dp, bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.allUserTodo.isEmpty()) {
                    EmptyContent()
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchField(state = textState, placeholderText = "Search")
                    }

                    LazyColumn {
                        items(viewModel.allUserTodo.filter {
                            it.title.contains(searchedText, ignoreCase = true)
                        }.sortedBy { it.date }, key = { it.id }) { todoItem ->
                            TodoCardItems(
                                todoItem = todoItem,
                                onCheckChange = {
                                    viewModel.onTodoCheck(todoItem)
                                },
                                sheetValue = sheetValue,
                                onClick = {
                                    currentBottomSheet.let { BottomSheetType.TYPE2 }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
//    DisposableEffect(viewModel) {
//        viewModel.addListener()
//        onDispose {
//            viewModel.removeListener()
//        }
//    }

}

@Composable
fun navDrawerItems(): List<NavigationDrawerItem> {
    val listItems = arrayListOf<NavigationDrawerItem>()

    listItems.add(
        NavigationDrawerItem(
            R.drawable.brush,
            "Change app color",
            R.drawable.arrow_forward,
            "Change Theme"
        )
    )
    listItems.add(
        NavigationDrawerItem(
            R.drawable.text,
            "Change app typography",
            R.drawable.arrow_forward,
            "Change typography"
        ),
    )
    listItems.add(
        NavigationDrawerItem(
            R.drawable.language,
            "Change app language",
            R.drawable.arrow_forward,
            "Change language"
        ),
    )

    listItems.add(
        NavigationDrawerItem(
            R.drawable.import_icon,
            "Import from google calender",
            R.drawable.arrow_forward,
            "Import calender"
        ),
    )
    return listItems
}
