package com.example.to_dolist.presentation.edit_task.components

import android.graphics.Bitmap
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.white

@Composable
fun TaskImages(
    imageList: List<Bitmap>,
    onDeleteImage: (Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(10.dp)
            .horizontalScroll(
                rememberScrollState()
            )
    ) {
        imageList.forEachIndexed { index, bitmap ->
            Box {
                AsyncImage(
                    model = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .height(100.dp)
                        .width(100.dp),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(25.dp),
                    color = white,
                    elevation = 5.dp,
                    shape = CircleShape,
                    ) {
                    IconButton(
                        onClick = {
                            onDeleteImage(index)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = black,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}