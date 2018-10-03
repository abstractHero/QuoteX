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
package com.phantasmdragon.quote.callbackLayer.receiver

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import com.phantasmdragon.quote.backgroundLevel.NotificationWorker.Companion.QUOTE_NOTIFICATION_ID
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.dataLayer.repository.DatabaseQuoteRepository
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class NotificationsActionBroadcastReceiver: DaggerBroadcastReceiver() {

    companion object {
        const val ACTION_BROADCAST_REQUEST_CODE = 2
        const val ACTION_ADD_TO_FAVOURITE = "action_add_to_favourite"

        const val EXTRA_QUOTE_TEXT = "action_quote_text"
        const val EXTRA_QUOTE_AUTHOR = "action_quote_author"
    }

    @Inject lateinit var databaseQuoteRepository: DatabaseQuoteRepository
    @Inject lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action != ACTION_ADD_TO_FAVOURITE) return

        val quoteText = intent.getStringExtra(EXTRA_QUOTE_TEXT)
        val quoteAuthor = intent.getStringExtra(EXTRA_QUOTE_AUTHOR)

        addQuoteToFavourite(Quote(0,
                                  quoteText,
                                  quoteAuthor))
        cancelNotification()
    }

    private fun addQuoteToFavourite(quote: Quote) {
        databaseQuoteRepository.insert(quote)
    }

    private fun cancelNotification() {
        notificationManager.cancel(QUOTE_NOTIFICATION_ID)
    }

}