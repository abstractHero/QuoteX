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
package com.phantasmdragon.quote.dataLayer.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.phantasmdragon.quote.dataLayer.database.dao.AuthorDao
import com.phantasmdragon.quote.dataLayer.database.dao.QuoteDao
import com.phantasmdragon.quote.dataLayer.database.entity.AuthorEntity
import com.phantasmdragon.quote.dataLayer.database.entity.QuoteEntity

@Database(entities = [QuoteEntity::class,
                      AuthorEntity::class],
          version = 3)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun getQuoteDao(): QuoteDao
    abstract fun getAuthorDao(): AuthorDao

}