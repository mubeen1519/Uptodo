package com.stellerbyte.uptodo.screens.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.AccountNameDialog
import com.stellerbyte.uptodo.components.DeleteAccountDialog
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.components.LogoutDialog
import com.stellerbyte.uptodo.navigation.ABOUT_US_SCREEN
import com.stellerbyte.uptodo.services.implementation.UserProfileData

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileScreen(
    navigate: (String) -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    isLoading = profileViewModel.isLoading.value
    val accountName: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val accountPassword: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val logout: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val deleteAccountDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }


    var userDataFromFirebase by remember { mutableStateOf(UserProfileData()) }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    var userProfileImg by remember { mutableStateOf("") }
    userProfileImg = userDataFromFirebase.imageUrl

    var username by remember { mutableStateOf("") }
    username = userDataFromFirebase.username

    val completedTask by profileViewModel.completedTasksCount.collectAsState(initial = 0)
    val nonCompletedTask by profileViewModel.nonCompletedTasksCount.collectAsState(initial = 0)
    var accountUsername by remember {
        mutableStateOf(username)
    }

    val scrollState = rememberScrollState()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.profileTitle),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Box(contentAlignment = Alignment.Center) {
                        PickImageFromGallery(profilePictureUrlForCheck = userProfileImg) {
                            if (it != null) {
                                profileViewModel.uploadImageToFirebase(it)
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = username,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp), horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 140.dp, height = 55.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                            .clip(RoundedCornerShape(5.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$nonCompletedTask Task Left",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(width = 140.dp, height = 55.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                            .clip(RoundedCornerShape(5.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$completedTask Task done",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                //  Setting row
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 20.dp, start = 12.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                ) {
//                    Text(
//                        stringResource(id = R.string.settings),
//                        color = MaterialTheme.colorScheme.surfaceVariant,
//                        style = MaterialTheme.typography.labelSmall
//                    )
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    DrawableIcon(
//                        painter = painterResource(id = R.drawable.setting),
//                        contentDescription = "setting",
//                        tint = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.size(22.dp)
//                    )
//                    Text(
//                        text = stringResource(id = R.string.changeSetting),
//                        color = MaterialTheme.colorScheme.onSurface,
//                        style = MaterialTheme.typography.labelSmall,
//                        modifier = Modifier.padding(start = 10.dp)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    DrawableIcon(
//                        painter = painterResource(id = R.drawable.arrow_forward),
//                        contentDescription = "forward",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
                // Account row
                if (accountName.value) {
                    AccountNameDialog(
                        dialogState = accountName,
                        username = accountUsername,
                        onUsernameChange = {
                            accountUsername = it
                        },
                        onChangedClick = {
                            profileViewModel.changeUsername(accountUsername)
                        })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        stringResource(id = R.string.txtAccount),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                        .clickable { accountName.value = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.user_svg),
                        contentDescription = "user",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.accountName),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
//                if (accountPassword.value) {
//                    ChangePasswordDialog(dialogState = accountPassword)
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 25.dp, start = 10.dp, end = 10.dp)
//                        .clickable { accountPassword.value = true },
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    DrawableIcon(
//                        painter = painterResource(id = R.drawable.key),
//                        contentDescription = "password",
//                        tint = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.size(22.dp)
//                    )
//                    Text(
//                        text = stringResource(id = R.string.accountPassword),
//                        color = MaterialTheme.colorScheme.onSurface,
//                        style = MaterialTheme.typography.labelSmall,
//                        modifier = Modifier.padding(start = 10.dp)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    DrawableIcon(
//                        painter = painterResource(id = R.drawable.arrow_forward),
//                        contentDescription = "forward",
//                        tint = MaterialTheme.colorScheme.onSurface,
//                    )
//                }

                // uptodo row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigate(ABOUT_US_SCREEN)
                        }
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.about_us),
                        contentDescription = "about us",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.aboutUs),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 25.dp, start = 10.dp, end = 10.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    DrawableIcon(
//                        painter = painterResource(id = R.drawable.faq),
//                        contentDescription = "faq",
//                        tint = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.size(22.dp)
//                    )
//                    Text(
//                        text = stringResource(id = R.string.faq),
//                        color = MaterialTheme.colorScheme.onSurface,
//                        style = MaterialTheme.typography.labelSmall,
//                        modifier = Modifier.padding(start = 10.dp)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    DrawableIcon(
//                        painter = painterResource(id = R.drawable.arrow_forward),
//                        contentDescription = "forward",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:") // Ensures only email apps handle this
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("mubeen1519@example.com"))
                                putExtra(Intent.EXTRA_SUBJECT, "Feedback to Developers")
                            }
                            context.startActivity(emailIntent)
                        }
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.feedback),
                        contentDescription = "faq",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.feedback),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = "faq",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.support),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (logout.value) {
                    LogoutDialog(
                        dialogState = logout,
                        logout = {
                            profileViewModel.logout(navigate)
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp)
                        .clickable { logout.value = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "logout",
                        tint = Color.Red,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.logout),
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                if (deleteAccountDialog.value) {
                    DeleteAccountDialog(
                        dialogState = deleteAccountDialog,
                        navigate = navigate
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        stringResource(id = R.string.deletion),
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 110.dp)
                        .clickable { deleteAccountDialog.value = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "logout",
                        tint = Color.Red,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.delete_account),
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}