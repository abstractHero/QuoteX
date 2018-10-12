/*
 * Copyright 2018 abstractHero
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phantasmdragon.quote.utilsLevel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.callbackLayer.receiver.NotificationsActionBroadcastReceiver
import com.phantasmdragon.quote.callbackLayer.receiver.NotificationsActionBroadcastReceiver.Companion.ACTION_ADD_TO_FAVOURITE
import com.phantasmdragon.quote.callbackLayer.receiver.NotificationsActionBroadcastReceiver.Companion.EXTRA_QUOTE_AUTHOR
import com.phantasmdragon.quote.callbackLayer.receiver.NotificationsActionBroadcastReceiver.Companion.EXTRA_QUOTE_TEXT
import com.phantasmdragon.quote.dataLayer.json.Quote
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val sharedPreference: SharedPreferences
) : ContextWrapper(context) {

    private val vibrationPattern = longArrayOf(0, 200)

    init {
        setNotificationChannel()
    }

    private fun setNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val quoteChannel = NotificationChannel(QUOTE_CHANNEL_ID,
                                               getString(R.string.notification_channel_quotes),
                                               NotificationManager.IMPORTANCE_DEFAULT)
        quoteChannel.description = getString(R.string.notification_channel_quotes_descr)
        quoteChannel.enableVibration(isVibrated())
        quoteChannel.vibrationPattern = vibrationPattern
        quoteChannel.setSound(
            getSoundPath(),
            AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build()
        )

        notificationManager.createNotificationChannel(quoteChannel)
    }

    private fun isVibrated() = sharedPreference.getBoolean(getString(R.string.settings_key_vibration), false)

    private fun getSoundPath() = Uri.parse(sharedPreference.getString(getString(R.string.settings_key_sound), ""))

    fun getQuoteNotification(quote: Quote?): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat.Builder(context, QUOTE_CHANNEL_ID)
                .setContentTitle(getString(R.string.push_title))
                .setContentText(getString(R.string.push_collapsed_subtitle))
                .setSmallIcon(R.drawable.ic_push)
                .setStyle(NotificationCompat.BigTextStyle()
                                            .bigText(quote?.toShareView()))
                .addAction(R.drawable.ic_favorite,
                           getString(R.string.push_action_like),
                           getActionPendingIntent(quote))

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setSound(getSoundPath())
            if (isVibrated()) notificationBuilder.setVibrate(vibrationPattern)
        }

        return notificationBuilder
    }

    private fun getActionPendingIntent(quote: Quote?) =
        PendingIntent.getBroadcast(
            this,
            NotificationsActionBroadcastReceiver.ACTION_BROADCAST_REQUEST_CODE,
            getActionIntent(quote),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getActionIntent(quote: Quote?) = Intent(this, NotificationsActionBroadcastReceiver::class.java).apply {
        action = ACTION_ADD_TO_FAVOURITE
        putExtra(EXTRA_QUOTE_TEXT, quote?.quoteText)
        putExtra(EXTRA_QUOTE_AUTHOR, quote?.quoteAuthor)
    }

    fun notify(notificationId: Int, notification: NotificationCompat.Builder) {
        notificationManager.notify(notificationId, notification.build())
    }

    companion object {
        const val QUOTE_CHANNEL_ID = "quote_channel_id"
    }

}