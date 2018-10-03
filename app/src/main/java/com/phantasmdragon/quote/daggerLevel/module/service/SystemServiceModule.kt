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
package com.phantasmdragon.quote.daggerLevel.module.service

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import com.phantasmdragon.quote.QuoteApplication
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class SystemServiceModule {

    @Provides
    @ApplicationScope
    fun provideConnectivityManager(application: QuoteApplication) = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @ApplicationScope
    fun provideLayoutInflater(application: QuoteApplication) = application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @Provides
    @ApplicationScope
    fun provideAlarmManager(application: QuoteApplication) = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @Provides
    @ApplicationScope
    fun provideNotificationManager(application: QuoteApplication) = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

}