package com.example.to_dolist.presentation.common

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.to_dolist.R
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.darkGrey
import com.example.to_dolist.presentation.theme.lightBlue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MyDatePicker(
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    context: Context,
    onChooseTime: (String) -> Unit,
) {
    val selDate = remember { mutableStateOf(LocalDate.now()) }

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    var timePicker by remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            timePicker = if (hour < 10) {
                onChooseTime("0$hour:$minute")
                "0$hour:$minute"
            } else {
                onChooseTime("$hour:$minute")
                "$hour:$minute"
            }

        },
        mHour, mMinute, true,
    )

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = lightBlue,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.pick_date),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = selDate.value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            CustomCalendarView(onDateSelected = {
                selDate.value = it
            })

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_time),
                    contentDescription = "Time Icon",
                    tint = darkGrey
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = stringResource(R.string.time), style = Typography.titleSmall)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.clickable {
                        timePickerDialog.show()
                    },
                    text = if (timePicker == "") stringResource(R.string.no) else timePicker,
                    color = darkGrey,
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "HỦY",
                        style = Typography.headlineSmall,
                        color = lightBlue
                    )
                }
                TextButton(
                    onClick = {
                        onDateSelected(selDate.value)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.done),
                        style = Typography.headlineSmall,
                        color = lightBlue,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}

@Composable
fun CustomCalendarView(onDateSelected: (LocalDate) -> Unit) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, R.style.CalenderViewCustom))
        },
        update = { view ->
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    LocalDate
                        .now()
                        .withMonth(month + 1)
                        .withYear(year)
                        .withDayOfMonth(dayOfMonth)
                )
            }
        }
    )
}