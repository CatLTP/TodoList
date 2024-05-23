package com.example.to_dolist.presentation.tag.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.to_dolist.domain.model.Tag
import com.example.to_dolist.presentation.tag.helper.TagDropdownMenu
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.lightBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TagItem(
    modifier: Modifier,
    tag: Tag,
    onClickMoreVert: () -> Unit,
    shouldShowDropdownMenu: Boolean,
    onDismissRequest: () -> Unit,
    onClickDropdownOption: (option: TagDropdownMenu) -> Unit,
) {
    val options = TagDropdownMenu.entries.toTypedArray()

    Row(
        modifier = modifier,
    ) {
        ListItem(
            icon = {
                OutlinedButton(
                    onClick = { },
                    shape = CircleShape,
                    modifier = Modifier.size(13.dp),
                    border = BorderStroke(width = 0.dp, color = Color.Transparent),
                    colors = ButtonDefaults.buttonColors(lightBlue)
                ) {}
            },
            text = {
                Text(
                    text = tag.name, style = Typography.titleMedium,
                )
            },
            trailing = {
                IconButton(onClick = onClickMoreVert) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More option"
                    )
                }
            }
        )

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Spacer(modifier = Modifier.weight(1f))
            DropdownMenu(
                expanded = shouldShowDropdownMenu,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(
                    clippingEnabled = false
                )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onClickDropdownOption(option)
                        },
                    ) {
                        Text(option.title, style = Typography.titleSmall)
                    }
                }
            }
        }

    }

}