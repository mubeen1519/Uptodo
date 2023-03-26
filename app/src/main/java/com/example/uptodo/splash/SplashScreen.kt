package com.example.uptodo.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uptodo.navigation.Intro_Pages
import kotlinx.coroutines.delay

@Composable
fun Splash(navigate : (String) -> Unit,viewModel: SplashViewModel = hiltViewModel()) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val offset by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 1000,
        )
    )

    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    
    LaunchedEffect(key1 = true, block = {
        startAnimation = true
        delay(3000)
        navigate(Intro_Pages)
        viewModel.onAppStart(navigate)

    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .offset(offset)
                .alpha(alphaState),
            painter = painterResource(id = com.example.uptodo.R.drawable.ic_logo),
            contentDescription = "Todo logo",
        )
    }
}

