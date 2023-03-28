package com.example.uptodo.crousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

fun createPages() = listOf(
    ImageData(
        com.example.uptodo.R.drawable.ic_task,
        "Manage your tasks",
        "You can easily manage all of your daily tasks in DoMe for free"
    ),
    ImageData(
        com.example.uptodo.R.drawable.ic_routine,
        "Create daily routine",
        "In Uptodo you can create your personalized routine to stay productive"
    ),
    ImageData(
        com.example.uptodo.R.drawable.ic_organize,
        "Organize your tasks",
        "You can organize your daily tasks by adding your tasks into separate categories"
    ),
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPages() {
    Row(modifier = Modifier.fillMaxWidth())
    {
        val pages = createPages()
        val pagerState = rememberPagerState()

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 20.dp)
        ) { currentPage ->
            Column() {
                Image(painter = painterResource(id = pages[currentPage].image), contentDescription = "Images", modifier = Modifier.fillMaxWidth().size(250.dp).padding(bottom = 40.dp))
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
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 35.dp)
                )
                Text(
                    text = pages[currentPage].description,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 30.dp, top = 25.dp, start = 20.dp, end = 20.dp).align(Alignment.CenterHorizontally)
                )
            }

        }
    }
}

