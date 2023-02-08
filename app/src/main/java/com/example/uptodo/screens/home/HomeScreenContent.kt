package com.example.uptodo.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon

@Composable
fun HomeScreenContent(
    viewModel: HomeViewModel = hiltViewModel(),
    todoId: String,

) {

    LaunchedEffect(viewModel) {
        viewModel.getTodo(todoId)
        viewModel.initailizeTodo()
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
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
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(40.dp)) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.dropdown),
                        contentDescription = "dropdown",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
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
                LazyColumn {
                    items(viewModel.allUserTodo, key = {it.id}) { todoItem ->
                        TodoCardItems(todoItem = todoItem, onCheckChange = {
                            viewModel.onTodoCheck(todoItem)
                        },)
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
