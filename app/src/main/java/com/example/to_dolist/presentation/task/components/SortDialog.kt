package com.example.to_dolist.presentation.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.to_dolist.R
import com.example.to_dolist.domain.util.OrderType
import com.example.to_dolist.presentation.task.TaskState
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.lightBlue

@Composable
fun SortDialog(
    taskState: TaskState,
    onCheckChange: (newValue: OrderType) -> Unit,
    onFinishChoosing: () -> Unit,
) {
    val optionList = OrderType.entries.toTypedArray()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(size = 20.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Các nhiệm vụ được sắp xếp theo",
            style = Typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(10.dp))

        for (i in optionList) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = taskState.selectedSortOption == i,
                    onCheckedChange = { newValue ->
                        if (newValue) {
                            onCheckChange(i)
                        }
                    },
                )
                Text(
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 16.dp
                    ),
                    text = i.title,
                    style = Typography.bodyLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onFinishChoosing) {
                Text(
                    text = stringResource(R.string.choose),
                    style = Typography.titleMedium,
                    color = lightBlue
                )
            }
        }

    }
}