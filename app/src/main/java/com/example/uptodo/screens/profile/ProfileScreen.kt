package com.example.uptodo.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.uptodo.R
import com.example.uptodo.components.DrawableIcon
import com.example.uptodo.components.categories.AccountNameDialog
import com.example.uptodo.components.categories.ChangePasswordDialog
import com.example.uptodo.components.categories.LogoutDialog
import com.example.uptodo.services.implementation.UserProfileData
import com.example.uptodo.ui.theme.BottomBarColor

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
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

    var userDataFromFirebase by remember { mutableStateOf(UserProfileData()) }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    var userProfileImg by remember { mutableStateOf("") }
    userProfileImg = userDataFromFirebase.imageUrl

    var email by remember { mutableStateOf("") }
    email = userDataFromFirebase.username

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
                Text(text = "Profile", color = Color.White, fontSize = 20.sp)
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
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = email, color = Color.White, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp), horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 140.dp, height = 55.dp)
                            .background(BottomBarColor)
                            .clip(RoundedCornerShape(5.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "10 Task Left", color = Color.White)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(width = 140.dp, height = 55.dp)
                            .background(BottomBarColor)
                            .clip(RoundedCornerShape(5.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "5 Task done", color = Color.White)
                    }
                }
                //  Setting row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Settings", color = Color.LightGray)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.setting),
                        contentDescription = "setting",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Change app Settings",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White
                    )
                }
                // Account row
                if (accountName.value) {
                    AccountNameDialog(dialogState = accountName)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Account", color = Color.LightGray)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                        .clickable { accountName.value = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "user",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Change account name",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White,
                    )
                }
                if (accountPassword.value) {
                    ChangePasswordDialog(dialogState = accountPassword)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp)
                        .clickable { accountPassword.value = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.key),
                        contentDescription = "password",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Change account password",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White,
                    )
                }

                // uptodo row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Uptodo", color = Color.LightGray)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.about_us),
                        contentDescription = "about us",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "About us",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.faq),
                        contentDescription = "faq",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "FAQ",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.feedback),
                        contentDescription = "faq",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Help & Feedback",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White
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
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Support Us",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White
                    )
                }
                if (logout.value) {
                    LogoutDialog(dialogState = logout, navController = navHostController)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 10.dp, end = 10.dp, bottom = 110.dp)
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
                        text = "Logout",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DrawableIcon(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = "forward",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}