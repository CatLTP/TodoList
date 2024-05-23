package com.example.to_dolist.presentation.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.to_dolist.presentation.task.helper.TaskAppBarMenu
import com.example.to_dolist.presentation.task.TaskState
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.white

@Composable
fun AppBar(
    taskState: TaskState,
    onClickTagButton: (pos: Int) -> Unit,
    onClickAppBarMenu: (option: TaskAppBarMenu) -> Unit,
    onClickBackButtonInSearchMode: () -> Unit,
    onSearchValueChange: (newValue: String) -> Unit,
) {
    var expandedDropdown by remember { mutableStateOf(false) }
    val options = TaskAppBarMenu.entries.toTypedArray()

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = white
    ) {
        if (!taskState.showSearchBar) {
            Row {
                LazyRow(
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        end = 10.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(taskState.tags) { tag ->
                        if (taskState.selectedTag == tag.id) {
                            TagButtonItem(
                                tag,
                                textColor = white,
                                buttonColor = lightBlue,
                                onClick = {
                                    onClickTagButton(0)
                                })
                        } else {
                            TagButtonItem(
                                tag,
                                onClick = {
                                    onClickTagButton(tag.id)
                                }
                            )
                        }
                    }
                }
                IconButton(onClick = {
                    expandedDropdown = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Dropdown menu")
                }
            }
        } else {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = white,
                    textColor = black
                ),
                singleLine = true,
                placeholder = {
                    Text(text = "Tìm kiếm", style = Typography.labelLarge)
                },
                textStyle = Typography.bodyLarge,
                leadingIcon = {
                    IconButton(onClick = onClickBackButtonInSearchMode) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "navigate back",
                        )
                    }
                },
                value = taskState.searchValue,
                onValueChange = {
                    onSearchValueChange(it)
                },
            )
        }


        Row {
            Spacer(modifier = Modifier.weight(1f))
            DropdownMenu(
                expanded = expandedDropdown,
                onDismissRequest = { expandedDropdown = false },
                properties = PopupProperties(
                    clippingEnabled = false
                )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            expandedDropdown = false
                            onClickAppBarMenu(option)
                        },
                    ) {
                        Text(option.title, style = Typography.titleSmall)
                    }
                }
            }
        }
    }
}
