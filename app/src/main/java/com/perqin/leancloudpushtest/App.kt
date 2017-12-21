package com.perqin.leancloudpushtest

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.avos.avoscloud.*

/**
 * Created on 12/21/17.
 *
 * @author perqin
 */
private const val CHANNEL_ID_DEFAULT = "default"

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel
            createChannel()
            PushService.setDefaultChannelId(this, CHANNEL_ID_DEFAULT)
        }
        AVOSCloud.initialize(this, BuildConfig.LC_APP_ID, BuildConfig.LC_APP_KEY)
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG)
        AVInstallation.getCurrentInstallation().saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e != null) {
                    Toast.makeText(this@App, "Fail to create installation: " + e.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@App, "Created installation", Toast.LENGTH_SHORT).show()
                }
            }
        })
        PushService.setDefaultPushCallback(this, MainActivity::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val normalChannel = NotificationChannel(CHANNEL_ID_DEFAULT, "Default channel", NotificationManager.IMPORTANCE_LOW)
        normalChannel.description = "Default channel"
        normalChannel.enableLights(false)
        normalChannel.enableVibration(false)
        nm.createNotificationChannel(normalChannel)
    }
}
