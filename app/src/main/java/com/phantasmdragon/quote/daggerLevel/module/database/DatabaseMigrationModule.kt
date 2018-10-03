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

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseMigrationModule {

    @Provides
    @ApplicationScope
    fun provideMigrationSet(): Array<Migration> = arrayOf(object : Migration(1, 2) {

        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE UNIQUE INDEX index_Quotes_authorId ON Quotes(authorId)")
        }

    }, object : Migration(2, 3) {

        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""ALTER TABLE Quotes
                                     ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0""")

            database.execSQL("""ALTER TABLE Quotes
                                     ADD COLUMN deletedOrder INTEGER NOT NULL DEFAULT 0""")
        }

    })

}