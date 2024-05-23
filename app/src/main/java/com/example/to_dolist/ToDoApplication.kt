package com.example.to_dolist

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

var PACKAGE_NAME: String? = null

@HiltAndroidApp
class ToDoApplication : Application() {
    companion object {
        const val CHANNEL_ID = "reminder_id"
    }

    override fun onCreate() {
        super.onCreate()
        PACKAGE_NAME = applicationContext.packageName

        val channelName = "reminder"
        val descriptionText = "Hello there"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            importance
        ).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}