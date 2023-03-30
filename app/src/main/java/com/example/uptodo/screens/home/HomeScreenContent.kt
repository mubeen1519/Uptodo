package com.example.uptodo.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.DrawerValue
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.SearchField
import com.example.uptodo.components.categories.ChangeLanguageDialog
import com.example.uptodo.components.categories.NavigationDrawerItem
import com.example.uptodo.components.categories.TypographyDialog
import com.example.uptodo.navigation.BottomBar
import com.example.uptodo.screens.profile.ProfileViewModel
import com.example.uptodo.screens.settings.ChangeThemeDialog
import com.example.uptodo.services.implementation.UserProfileData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreenContent(
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    openSheet: (String) -> Unit,
    todoId: String,
    navController: NavHostController
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
    val typeDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val languageDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }


    var userDataFromFirebase by remember { mutableStateOf(UserProfileData()) }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value
    var userProfileImg by remember { mutableStateOf("") }
    userProfileImg = userDataFromFirebase.imageUrl

    val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getTodo(todoId)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (themeDialog.value) {
            ChangeThemeDialog(dialogState = themeDialog, userSetting = viewModel.themeSetting)
        }

        if (typeDialog.value) {
            TypographyDialog(dialogState = typeDialog)
        }

        if(languageDialog.value){
            ChangeLanguageDialog(state = languageDialog)
        }

        ModalDrawer(
            drawerState = drawerState,
            drawerBackgroundColor = MaterialTheme.colorScheme.background,
            drawerContent = {
                val itemsList = navDrawerItems()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 24.dp)
                ) {

                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            closeDrawer()
                        }
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 30.dp))

                    androidx.compose.material.Text(
                        text = stringResource(id = R.string.settings),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    this.itemsIndexed(items = itemsList) { index,item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 20.dp)
                                .clickable {
                                    if (index == 0) {
                                        themeDialog.value = true
                                    }
                                    if(index == 1){
                                        typeDialog.value = true
                                    }
                                    if(index == 2){
                                        languageDialog.value = true
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = item.icon),
                                contentDescription = "icons",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(25.dp)
                            )

                            androidx.compose.material.Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                DrawableIcon(
                                    painter = painterResource(id = item.forwardIcon),
                                    contentDescription = "forward",
                                    tint = MaterialTheme.colorScheme.onSurface,
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
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.HomeHeading),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    if (userProfileImg != "") {
                        Box(modifier = Modifier
                            .clip(CircleShape)
                            .clickable { navController.navigate(BottomBar.Profile.route) }) {
                            Image(
                                painter = rememberAsyncImagePainter(userProfileImg),
                                contentDescription = "Profile",
                                modifier = Modifier.size(35.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { navController.navigate(BottomBar.Profile.route) }) {
                            Image(
                                painter = painterResource(id = R.drawable.user_svg),
                                contentDescription = "Profile",
                                modifier = Modifier.size(30.dp),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)

                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp, start = 10.dp, end = 10.dp, bottom = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (tasks.value.isEmpty()) {
                    EmptyContent()
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchField(state = textState)
                    }

                    LazyColumn {
                        items(tasks.value.filter {
                            it.title.contains(searchedText, ignoreCase = true)
                        }.sortedBy { it.date }, key = { it.id }) { todoItem ->
                            TodoCardItems(
                                todoItem = todoItem,
                                onCheckChange = {
                                    viewModel.onTodoCheck(todoItem)
                                },
                                sheetValue = sheetValue,
                                onClick = {
                                    viewModel.onTodoClick(openSheet, todoItem)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
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
