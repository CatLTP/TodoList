package com.example.to_dolist.presentation.edit_task.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_dolist.presentation.edit_task.helper.TaskSettingData
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.gray
import com.example.to_dolist.presentation.theme.lightBlue

@Composable
fun TaskSettingItem(
    data: TaskSettingData,
) {
    Column(
        modifier = Modifier.clickable {
            data.onClick()
        },
    ) {
        Divider(
            thickness = 1.dp,
            color = gray
        )
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = data.painter,
                contentDescription = null,
                tint = darkGrey,
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = data.title,
                color = darkGrey,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                shape = CircleShape,
                color = lightBlue.copy(alpha = 0.3f),
            ) {
                Text(
                    text = data.value,
                    color = darkGrey,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
        Divider(
            thickness = 1.dp,
            color = gray
        )
    }

}