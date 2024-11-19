package com.stellerbyte.uptodo.screens.splash

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stellerbyte.uptodo.crousel.createPages
import com.stellerbyte.uptodo.navigation.Create_Account
import com.stellerbyte.uptodo.ui.theme.Purple40
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pages(
    navigate: (String) -> Unit
) {
    val pages = createPages()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "SKIP",
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp, start = 10.dp)
                .clickable { navigate(Create_Account) }
        )
        Row(modifier = Modifier.padding(top = 70.dp)) {

            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 20.dp)
            ) { currentPage ->
                Column {
                    Image(
                        painter = painterResource(id = pages[currentPage].image),
                        contentDescription = "Images",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(250.dp)
                            .padding(bottom = 40.dp)
                    )
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        inactiveColor = Color.LightGray,
                        activeColor = Color.DarkGray,
                        indicatorHeight = 4.dp,
                        indicatorWidth = 28.dp,
                        spacing = 8.dp

                    )
                    Text(
                        text = pages[currentPage].title,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 35.dp)
                    )
                    Text(
                        text = pages[currentPage].description,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 30.dp, top = 25.dp, start = 20.dp, end = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Back",
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            } else {
                                onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()
                            }
                        }
                    }
                    .weight(1f)
                    .padding(30.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navigate(Create_Account)
                        }
                    }
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 16.dp)
            ) {
                Text(text = if(pagerState.currentPage < pages.size - 1)"NEXT" else "GET STARTED")
            }
        }

    }
}

