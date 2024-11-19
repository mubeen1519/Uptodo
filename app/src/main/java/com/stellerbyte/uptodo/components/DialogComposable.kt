package com.stellerbyte.uptodo.components

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.navigation.DeleteScreen
import com.stellerbyte.uptodo.navigation.Graph
import com.stellerbyte.uptodo.screens.category.Icons
import com.stellerbyte.uptodo.screens.category.Priority
import com.stellerbyte.uptodo.screens.home.HomeViewModel
import com.stellerbyte.uptodo.ui.theme.AppThemeTypography
import com.stellerbyte.uptodo.ui.theme.Purple40
import com.stellerbyte.uptodo.ui.theme.latoFamily
import com.stellerbyte.uptodo.ui.theme.lobsterFamily
import com.stellerbyte.uptodo.ui.theme.quickSandFamily

@Composable
fun CategoryDialog(
    state: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    CommonDialog(state = state) {
        BodyContent(
            dialogState = state,
            viewModel = viewModel
        ) {}
    }
}

@Composable
private fun BodyContent(
    viewModel: HomeViewModel = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    onItemSelection: (selectedItemIndex: Int) -> Unit,
) {
    val selectedIndex = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.chooseCategory),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
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
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (selectedIndex.value == index) Purple40 else MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(width = 60.dp, height = 60.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(icons.color),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = {
                                selectedIndex.value = index
                                onItemSelection(selectedIndex.value)
                                viewModel.onIconChange(icons)
                            },
                        ) {
                            DrawableIcon(
                                painter = painterResource(id = icons.icon),
                                contentDescription = "icons",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Text(
                        text = icons.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
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
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.saveCategory),
                    style = MaterialTheme.typography.labelSmall
                )
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
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.priorityTitle),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
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
                            .size(width = 60.dp, height = 60.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(if (selectedIndex.value == index) Purple40 else MaterialTheme.colorScheme.onSecondary)
                            .padding(5.dp),
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
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        Text(
                            text = priority.value.toString(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 30.dp),

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
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    dialogState.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(width = 150.dp, height = 40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun AccountNameDialog(
    dialogState: MutableState<Boolean>,
    username: String,
    onUsernameChange: (String) -> Unit,
    onChangedClick : () -> Unit,
) {
    CommonDialog(state = dialogState) {
        ChangeAccountName(
            dialogState = dialogState,
            username = username,
            onUsernameChange = onUsernameChange,
            onChangedClick = onChangedClick
        )
    }
}

@Composable
private fun ChangeAccountName(
    username: String,
    dialogState: MutableState<Boolean>,
    onUsernameChange: (String) -> Unit,
    onChangedClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.accountName),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            placeholderText = stringResource(id = R.string.accountPlaceholder),
            onFieldChange = onUsernameChange,
            value = username
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onChangedClick()
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.edit),
                    style = MaterialTheme.typography.labelSmall
                )
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
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.accountPassword),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(20.dp)
    ) {

        InputField(
            placeholderText = stringResource(id = R.string.passwordPlaceholder),
            onFieldChange = {},
            label = stringResource(id = R.string.passwordPlaceholder),
            isFieldSecured = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        InputField(
            placeholderText = stringResource(id = R.string.newPasswordPlaceholder),
            onFieldChange = {},
            label = stringResource(id = R.string.newPasswordPlaceholder),
            isFieldSecured = true
        )

        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.edit),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun LogoutDialog(
    dialogState: MutableState<Boolean>,
    logout: () -> Unit,

) {
    CommonDialog(state = dialogState) {
        Logout(dialogState = dialogState, logout)
    }
}

@Composable
private fun Logout(
    dialogState: MutableState<Boolean>,
    logout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(id = R.string.logoutTxt),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = Purple40,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    logout()
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Purple40
                ),
                shape = RoundedCornerShape(5.dp)

            ) {
                Text(
                    text = stringResource(id = R.string.logout),
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
    }
}

@Composable
fun DeleteAccountDialog(
    dialogState: MutableState<Boolean>,
    navigate: (String) -> Unit
) {
    CommonDialog(state = dialogState) {
        DeleteAccountContent(dialogState = dialogState,navigate)
    }
}

@Composable
private fun DeleteAccountContent(
    dialogState: MutableState<Boolean>,
    navigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(id = R.string.deleteTxt),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dialogState.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = Purple40,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    navigate(DeleteScreen)
                    dialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(5.dp)

            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
    }
}


@Composable
fun TypographyDialog(
    dialogState: MutableState<Boolean>,
) {
    CommonDialog(state = dialogState) {
        ChangeTypography(dialogState = dialogState)
    }
}

@Composable
private fun ChangeTypography(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.typographyDialog),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(10.dp))

    }
    Column(
        modifier = modifier
            .selectableGroup()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .selectable(
                    AppThemeTypography.selectedFontFamily.value == latoFamily,
                    onClick = {
                        AppThemeTypography.selectedFontFamily.value = latoFamily
                        dialogState.value = false
                    },
                    role = Role.RadioButton
                )
                .padding(top = 15.dp, bottom = 8.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = AppThemeTypography.selectedFontFamily.value == latoFamily,
                onClick = null,
            )
            Spacer(modifier = modifier.width(5.dp))
            Text(
                stringResource(id = R.string.latoFont),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = latoFamily
            )
        }
        Row(
            modifier
                .fillMaxWidth()
                .selectable(
                    selected = AppThemeTypography.selectedFontFamily.value == quickSandFamily,
                    onClick = {
                        AppThemeTypography.selectedFontFamily.value = quickSandFamily
                        dialogState.value = false
                    },
                    role = Role.RadioButton
                )
                .padding(top = 15.dp, bottom = 8.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = AppThemeTypography.selectedFontFamily.value == quickSandFamily,
                onClick = null
            )
            Spacer(modifier = modifier.width(5.dp))
            Text(
                stringResource(id = R.string.quicksandFont),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = quickSandFamily
            )
        }

        Row(
            modifier
                .fillMaxWidth()
                .selectable(
                    selected = AppThemeTypography.selectedFontFamily.value == lobsterFamily,
                    onClick = {
                        AppThemeTypography.selectedFontFamily.value = lobsterFamily
                        dialogState.value = false
                    },
                    role = Role.RadioButton
                )
                .padding(top = 15.dp, bottom = 8.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = AppThemeTypography.selectedFontFamily.value == lobsterFamily,
                onClick = null
            )
            Spacer(modifier = modifier.width(5.dp))
            Text(
                stringResource(id = R.string.lobster),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = lobsterFamily
            )
        }
    }
}

@Composable
fun ChangeLanguageDialog(state: MutableState<Boolean>, context: Context) {
    CommonDialog(state = state) {
        ChangeLanguage(state = state, context)
    }
}

@Composable
private fun ChangeLanguage(
    state: MutableState<Boolean>,
    context: Context
) {

    val localeOption = mapOf(
        R.string.en to "en",
        R.string.fr to "fr",
        R.string.de to "de"
    ).mapKeys { stringResource(id = it.key) }

    var selected by remember {
        mutableStateOf(localeOption.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.changeLanguage),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .selectableGroup()
    ) {
        localeOption.keys.forEach { selectLocale ->
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = selected == selectLocale,
                    role = Role.RadioButton,
                    onClick = {
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(
                                localeOption[selectLocale]
                            )
                        )
                    }
                ), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = selected == selectLocale, onClick = null)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = selectLocale,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}









