package com.example.to_dolist.presentation.tag.components

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.to_dolist.R
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.presentation.tag.TagEvent
import com.example.to_dolist.presentation.tag.TagViewModel
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.lightGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTagDialog(
    onDismissRequest: () -> Unit,
    viewModel: TagViewModel,
) {
    val composableScope = rememberCoroutineScope()
    var tagTitle by remember {
        mutableStateOf("")
    }

    AlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp)),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(R.string.create_new_Tag),
                    style = Typography.titleMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier.clip(
                        RoundedCornerShape(10.dp)
                    ).height(100.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = lightGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = tagTitle,
                    onValueChange = {
                        tagTitle = it
                    },
                    maxLines = 5,
                    placeholder = {
                        Text(text = stringResource(R.string.enter_here))
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = Typography.titleSmall,
                            color = lightBlue.copy(
                                alpha = 0.5f
                            )
                        )
                    }
                    TextButton(onClick = {
                        composableScope.launch {
                            viewModel.onEvent(TagEvent.AddTag(Tag(name = tagTitle)))
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = Typography.titleSmall,
                            color = lightBlue
                        )
                    }
                }
            }
        }
    }
}