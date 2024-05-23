package com.example.to_dolist.presentation.edit_task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.darkBlue
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.lightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestMediaPermissionDialog(
    onDismissRequest: () -> Unit,
    onDecline: () -> Unit,
    onAgree: () -> Unit,
) {

    AlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Cấp quyền truy cập hình ảnh",
                    style = Typography.titleMedium,
                    color = darkBlue
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Vui lòng chỉnh lại quyền truy cập hình ảnh cho ứng dụng để có thể thêm các tập tin đính kèm vào")
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDecline) {
                        Text(text = "TỪ CHỐI", color = darkGrey)
                    }
                    TextButton(onClick = onAgree) {
                        Text(
                            text = "CHO PHÉP",
                            color = lightBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}