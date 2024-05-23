package com.example.to_dolist.domain.background

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.to_dolist.R
import com.example.to_dolist.ToDoApplication
import com.example.to_dolist.presentation.MainActivity

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {
    companion object {
        const val TITLE_KEY = "TITLE"
        const val CONTENT_KEY = "CONTENT"
    }

    private val notificationId = 17

    override fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent = PendingIntent.getActivity(
                applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
            val title = inputData.getString(TITLE_KEY)
            val content = inputData.getString(CONTENT_KEY)

            val notificationBuilder =
                NotificationCompat.Builder(applicationContext, ToDoApplication.CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_calendar_month_24)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(notificationId, notificationBuilder.build())
            }
            return Result.success()
        } else {
            return Result.failure()
        }
    }
}