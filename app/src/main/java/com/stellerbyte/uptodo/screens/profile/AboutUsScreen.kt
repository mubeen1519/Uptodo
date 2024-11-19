package com.stellerbyte.uptodo.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.navigation.BottomBar

@Composable
fun AboutUsScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                navHostController.navigate(BottomBar.Profile.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "About", color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSurface)
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_without_text),
                    contentDescription = "logo",
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Text(
            text = "Welcome to Stellerbyte!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = """
                At Stellerbyte, we specialize in crafting innovative and high-quality Android applications that empower businesses and delight users. With years of expertise in Android development, we have a proven track record of delivering exceptional mobile solutions that cater to a diverse range of industries.
                
                Our team is committed to turning ideas into impactful digital products. From e-commerce platforms to productivity tools, we design, develop, and deploy apps that are scalable, user-friendly, and performance-driven. Utilizing cutting-edge technologies like Jetpack Compose, Kotlin, Java, React Native, Room, My SQL, and Firebase, we ensure your app is built to meet the highest standards.
                
                Whether you’re looking for a full-fledged mobile application, custom UI/UX designs, or end-to-end Android development services, Stellerbyte is here to bring your vision to life. Let us help you create solutions that leave a lasting impression.
                
                Your success is our passion. Let’s build the future together!
            """.trimIndent(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Contact Information Section
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        val emailText = buildAnnotatedString {
            pushStringAnnotation(tag = "email", annotation = "mailto:mubeen1519@gmail.com")
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
            ) {
                append("mubeen1519@gmail.com")
            }
            pop()
        }

        ClickableText(
            text = emailText,
            onClick = { offset ->
                emailText.getStringAnnotations(offset, offset).firstOrNull()?.let { annotation ->
                    if (annotation.tag == "email") {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:") // Ensures only email apps handle this
                        }
                        context.startActivity(emailIntent)
                    }
                }
            }
        )
    }
}