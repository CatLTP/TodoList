package com.example.to_dolist.presentation.tag

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_dolist.domain.model.Screen
import com.example.to_dolist.presentation.tag.components.AddTagDialog
import com.example.to_dolist.presentation.tag.components.EditNameDialog
import com.example.to_dolist.presentation.tag.components.TagItem
import com.example.to_dolist.presentation.tag.helper.TagDropdownMenu
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TagScreen(
    onNavigateBack: () -> Unit,
    viewModel: TagViewModel,
    state: TagState,
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = white
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(25.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = "Navigate back"
                    )
                }
                Text(text = Screen.TagManagement.title, style = Typography.titleLarge)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = padding)
                .fillMaxWidth()
        ) {
            items(
                state.tags,
                key = { tag ->
                    tag.id
                },
            ) { tag ->
                TagItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                        )
                        .animateItemPlacement(),
                    tag,
                    onClickMoreVert = {
                        coroutineScope.launch {
                            viewModel.onEvent(TagEvent.OnClickMoreVert(tag))
                        }
                    },
                    shouldShowDropdownMenu = state.expandedTagDropdown == tag,
                    onDismissRequest = {
                        coroutineScope.launch {
                            viewModel.onEvent(TagEvent.OnClickMoreVert(null))
                        }
                    },
                    onClickDropdownOption = { option ->
                        when (option) {
                            TagDropdownMenu.Delete -> {
                                coroutineScope.launch {
                                    viewModel.onEvent(TagEvent.OnDeleteTag(tag))

                                }
                            }
                            TagDropdownMenu.ChangeName -> {
                                coroutineScope.launch {
                                    viewModel.onEvent(TagEvent.ShowEditNameDialog(tag, true))
                                }
                            }
                        }
                    }
                )
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                viewModel.onEvent(TagEvent.ShowAddTaskDialog(true))
                            }
                        }
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add tag",
                        tint = lightBlue,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Tạo mới",
                        color = lightBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        if (state.showAddTagDialog) {
            AddTagDialog(
                onDismissRequest = {
                    coroutineScope.launch {
                        viewModel.onEvent(TagEvent.ShowAddTaskDialog(false))
                    }
                },
                viewModel = viewModel
            )
        }

        if (state.showEditNameDialog) {
            EditNameDialog(
                onDismissRequest = {
                    coroutineScope.launch {
                        viewModel.onEvent(TagEvent.ShowEditNameDialog(null, false))
                    }
                },
                onSave = {
                    val tag = state.editingTag!!.copy(
                        name = it
                    )
                    coroutineScope.launch {
                        viewModel.onEvent(TagEvent.OnUpdateTag(tag))
                    }
                }
            )
        }
    }
}

