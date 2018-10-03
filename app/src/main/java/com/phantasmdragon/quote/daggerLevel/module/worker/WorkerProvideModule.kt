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
package com.phantasmdragon.quote.daggerLevel.module.worker

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ApplicationContext
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.utilsLevel.NotificationHelper
import dagger.Module
import dagger.Provides

@Module
class WorkerProvideModule {

    @Provides
    @ApplicationScope
    fun provideNotificationHelper1(@ApplicationContext context: Context,
                                  notificationManager: NotificationManager,
                                  sharedPreferences: SharedPreferences)
            = NotificationHelper(context,
                                 notificationManager,
                                 sharedPreferences)

}