package com.example.to_dolist.presentation.edit_task.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.to_dolist.presentation.edit_task.EditTaskEvent
import com.example.to_dolist.presentation.edit_task.EditTaskState
import com.example.to_dolist.presentation.edit_task.EditTaskViewModel
import com.example.to_dolist.presentation.theme.black
import com.example.to_dolist.presentation.theme.white
import kotlinx.coroutines.launch

@Composable
fun TaskNote(
    state: EditTaskState,
    viewModel: EditTaskViewModel,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    Log.i("TASK TITLE", state.taskTitle)
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = white,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "navigate back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                title = {
                    Text(text = state.taskTitle, color = black)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                value = state.note,
                onValueChange = {
                    coroutineScope.launch {
                        viewModel.onEvent(EditTaskEvent.OnNoteChange(it))
                    }
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = black
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}