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
package com.phantasmdragon.quote.daggerLevel.module.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ApplicationContext
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.dataLayer.database.QuoteDatabase
import com.phantasmdragon.quote.utilsLevel.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides

@Module(includes = [DatabaseMigrationModule::class])
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideDatabase(@ApplicationContext application: Context,
                        migrations: Array<@JvmSuppressWildcards Migration>)
            = Room.databaseBuilder(application,
                                   QuoteDatabase::class.java,
                                   DATABASE_NAME)
                  .addMigrations(*migrations)
                  .build()

    @Provides
    @ApplicationScope
    fun provideQuoteDao(quoteDatabase: QuoteDatabase) = quoteDatabase.getQuoteDao()

    @Provides
    @ApplicationScope
    fun provideAuthorDao(quoteDatabase: QuoteDatabase) = quoteDatabase.getAuthorDao()

}