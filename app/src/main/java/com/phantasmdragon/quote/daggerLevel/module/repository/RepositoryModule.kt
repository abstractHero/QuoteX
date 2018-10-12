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
package com.phantasmdragon.quote.daggerLevel.module.repository

import android.content.SharedPreferences
import android.content.res.Resources
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.dataLayer.database.dao.AuthorDao
import com.phantasmdragon.quote.dataLayer.database.dao.QuoteDao
import com.phantasmdragon.quote.dataLayer.repository.DatabaseQuoteRepository
import com.phantasmdragon.quote.networkLayer.api.QuoteApi
import com.phantasmdragon.quote.networkLayer.repository.QuoteRepository
import com.phantasmdragon.quote.networkLayer.repository.QuoteWorkerRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideQuoteRepository(quoteApi: QuoteApi, resources: Resources, sharedPreferences: SharedPreferences) =
            QuoteRepository(quoteApi, resources, sharedPreferences)

    @Provides
    @ApplicationScope
    fun provideQuoteServiceRepository(quoteApi: QuoteApi, resources: Resources, sharedPreferences: SharedPreferences) =
            QuoteWorkerRepository(quoteApi, resources, sharedPreferences)

    /*
    Repositories allow to work with the database are down below.
     */
    @Provides
    @ApplicationScope
    fun provideDatabaseQuoteRepository(quoteDao: QuoteDao, authorDao: AuthorDao, resources: Resources) =
            DatabaseQuoteRepository(quoteDao, authorDao, resources)

}