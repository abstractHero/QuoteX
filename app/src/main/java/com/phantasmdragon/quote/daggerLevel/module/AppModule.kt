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
package com.phantasmdragon.quote.daggerLevel.module

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.support.v7.preference.PreferenceManager
import com.phantasmdragon.quote.QuoteApplication
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ApplicationContext
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.daggerLevel.module.database.DatabaseModule
import com.phantasmdragon.quote.daggerLevel.module.network.NetworkModule
import com.phantasmdragon.quote.daggerLevel.module.receiver.ReceiverProvider
import com.phantasmdragon.quote.daggerLevel.module.repository.RepositoryModule
import com.phantasmdragon.quote.daggerLevel.module.service.SystemServiceModule
import com.phantasmdragon.quote.daggerLevel.module.worker.WorkerModule
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class,
                    DatabaseModule::class,
                    SystemServiceModule::class,
                    RepositoryModule::class,
                    ReceiverProvider::class,
                    WorkerModule::class])
class AppModule {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun provideApplicationContext(application: QuoteApplication): Context = application.applicationContext

    @Provides
    @ApplicationScope
    fun provideResources(application: QuoteApplication): Resources = application.resources

    @Provides
    @ApplicationScope
    fun provideDefaultSharedPreferences(@ApplicationContext context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

}