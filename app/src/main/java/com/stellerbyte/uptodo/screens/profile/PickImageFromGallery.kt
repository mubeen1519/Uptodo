package com.stellerbyte.uptodo.screens.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.stellerbyte.uptodo.R

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PickImageFromGallery(
    profilePictureUrlForCheck: String,
    size: Dp = 100.dp,
    onSelect: (Uri?) -> Unit = {},
) {
    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onSelect(uri)
    }

    Box(
        modifier = Modifier
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(key1 = imageUri) {
            if (imageUri != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.contentResolver,imageUri!!)
                 bitmap = ImageDecoder.decodeBitmap(source)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, imageUri!!)
                  bitmap =  ImageDecoder.decodeBitmap(source)

                }
            }
        }
        if (bitmap != null) {
            Image(
                painter = rememberAsyncImagePainter(bitmap),
                contentDescription = null,
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
                    .size(size),
                contentScale = ContentScale.Crop
            )
        } else {
            if (profilePictureUrlForCheck != "") {
                Image(painter = rememberAsyncImagePainter(profilePictureUrlForCheck),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { launcher.launch("image/*") }
                        .size(size),
                    contentScale = ContentScale.Crop)
            } else {
                Image(painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context).data(R.drawable.user_svg)
                        .build()
                ),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { launcher.launch("image/*") }
                        .size(size),
                    contentScale = ContentScale.Crop,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface)

                )
            }
        }
    }
}