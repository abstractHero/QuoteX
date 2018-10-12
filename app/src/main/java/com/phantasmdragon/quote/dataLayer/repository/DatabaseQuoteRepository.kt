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
package com.phantasmdragon.quote.dataLayer.repository

import android.content.res.Resources
import com.phantasmdragon.quote.dataLayer.database.dao.AuthorDao
import com.phantasmdragon.quote.dataLayer.database.dao.QuoteDao
import com.phantasmdragon.quote.dataLayer.database.entity.AuthorEntity
import com.phantasmdragon.quote.dataLayer.database.entity.QuoteEntity
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.utilsLevel.databaseThread
import com.phantasmdragon.quote.utilsLevel.getCorrectedAuthor
import javax.inject.Inject

class DatabaseQuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val authorDao: AuthorDao,
    private val resources: Resources
) {

    fun getAllQuotes(searchQuery: String) =
            if (searchQuery.isEmpty()) quoteDao.getAllQuotes() else quoteDao.getQuotesByQuery(searchQuery)

    /**
     * Basically, there is no chance that an author's name will be an empty string,
     * cause it's already been handled before; however, the AuthorEntity has a non-nullable author,
     * whereas, the Quote has a nullable one. That's why we MUST check it using the elvis operator.
     */
    fun insert(quote: Quote) = databaseThread {

        val authorEntity = AuthorEntity(quote.getCorrectedAuthor(resources) ?: "")
        val authorId = authorDao.insert(authorEntity)

        quoteDao.insert(QuoteEntity(authorId, quote.quoteText))
    }

    fun markAsDeleted(id: Long?, deletedOrder: Int) = databaseThread {
        quoteDao.markAsDeleted(id, deletedOrder)
    }

    fun unmarkDeleted(deletedOrder: Int) = databaseThread {
        quoteDao.unmarkDeleted(deletedOrder)
    }

}