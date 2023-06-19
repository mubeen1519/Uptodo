package com.example.uptodo.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.screens.category.Icons
import com.example.uptodo.screens.category.Priority
import com.example.uptodo.services.implementation.TODOItem
import com.example.uptodo.ui.theme.Purple40

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoCardItems(
    todoItem: TODOItem,
    onCheckChange: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onClick: () -> Unit
) {

    Card(
        onClick = {
            onClick()
        },
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 8.dp, end = 8.dp, start = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = todoItem.completed, onCheckedChange = { onCheckChange() },
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clip(CircleShape),
                colors = CheckboxDefaults.colors(
                    uncheckedColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    checkmarkColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    checkedColor = Purple40,
                )
            )

            Column {
                Row {
                    androidx.compose.material3.Text(
                        text = todoItem.title,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    androidx.compose.material3.Text(
                        text = viewModel.getDateAndTime(todoItem),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.weight(2f))
                    Button(
                        modifier = Modifier.height(35.dp).alpha(0.8f),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = when (todoItem.icon) {
                                Icons.Grocery -> Icons.Grocery.color
                                Icons.Design -> Icons.Design.color
                                Icons.Health -> Icons.Health.color
                                Icons.Home -> Icons.Home.color
                                Icons.Movie -> Icons.Movie.color
                                Icons.Music -> Icons.Music.color
                                Icons.Social -> Icons.Social.color
                                Icons.Sport -> Icons.Sport.color
                                Icons.University -> Icons.University.color
                                Icons.Work -> Icons.Work.color
                                null -> Icons.University.color
                            }
                        )
                    ) {
                        DrawableIcon(
                            painter = painterResource(
                                id = when (todoItem.icon) {
                                    Icons.Grocery -> Icons.Grocery.icon
                                    Icons.Design -> Icons.Design.icon
                                    Icons.Health -> Icons.Health.icon
                                    Icons.Home -> Icons.Home.icon
                                    Icons.Movie -> Icons.Movie.icon
                                    Icons.Music -> Icons.Music.icon
                                    Icons.Social -> Icons.Social.icon
                                    Icons.Sport -> Icons.Sport.icon
                                    Icons.University -> Icons.University.icon
                                    Icons.Work -> Icons.Work.icon
                                    null -> Icons.University.icon
                                }
                            ),
                            contentDescription = "category",
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(
                            text = when (todoItem.icon) {
                                Icons.Grocery -> Icons.Grocery.title
                                Icons.Design -> Icons.Design.title
                                Icons.Health -> Icons.Health.title
                                Icons.Home -> Icons.Home.title
                                Icons.Movie -> Icons.Movie.title
                                Icons.Music -> Icons.Music.title
                                Icons.Social -> Icons.Social.title
                                Icons.Sport -> Icons.Sport.title
                                Icons.University -> Icons.University.title
                                Icons.Work -> Icons.Work.title
                                null -> Icons.University.title
                            },
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            fontSize = 9.sp
                        )
                    }

                    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                        OutlinedButton(
                            modifier = Modifier
                                .size(width = 55.dp, height = 35.dp)
                                .padding(start = 8.dp, end = 8.dp)
                                .border(
                                    border = BorderStroke(1.dp, Purple40),
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
                                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            DrawableIcon(
                                painter = painterResource(
                                    id = when (todoItem.priority) {
                                        Priority.Priority1 -> Priority.Priority1.icon
                                        Priority.Priority2 -> Priority.Priority2.icon
                                        Priority.Priority3 -> Priority.Priority3.icon
                                        Priority.Priority4 -> Priority.Priority4.icon
                                        Priority.Priority5 -> Priority.Priority5.icon
                                        Priority.Priority6 -> Priority.Priority6.icon
                                        Priority.Priority7 -> Priority.Priority7.icon
                                        Priority.Priority8 -> Priority.Priority8.icon
                                        Priority.Priority9 -> Priority.Priority9.icon
                                        Priority.Priority10 -> Priority.Priority10.icon
                                        null -> Priority.Priority1.icon
                                    }
                                ),
                                contentDescription = "some",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(10.dp)
                            )
                            Text(
                                text = when (todoItem.priority) {
                                    Priority.Priority1 -> Priority.Priority1.value.toString()
                                    Priority.Priority2 -> Priority.Priority2.value.toString()
                                    Priority.Priority3 -> Priority.Priority3.value.toString()
                                    Priority.Priority4 -> Priority.Priority4.value.toString()
                                    Priority.Priority5 -> Priority.Priority5.value.toString()
                                    Priority.Priority6 -> Priority.Priority6.value.toString()
                                    Priority.Priority7 -> Priority.Priority7.value.toString()
                                    Priority.Priority8 -> Priority.Priority8.value.toString()
                                    Priority.Priority9 -> Priority.Priority9.value.toString()
                                    Priority.Priority10 -> Priority.Priority10.value.toString()
                                    null -> Priority.Priority1.value.toString()
                                },
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

