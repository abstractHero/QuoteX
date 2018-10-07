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
package com.phantasmdragon.quote.dataLayer.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.phantasmdragon.quote.dataLayer.database.entity.QuoteEntity
import com.phantasmdragon.quote.dataLayer.json.Quote

@Dao
interface QuoteDao {

    @Query("""SELECT Quotes.id AS quoteId, Quotes.quoteText, Authors.name AS quoteAuthor
                    FROM Quotes
                    INNER JOIN Authors ON Quotes.authorId = Authors.id
                    WHERE isDeleted = 0""")
    fun getAllQuotes(): DataSource.Factory<Int, Quote>

    @Insert(onConflict = REPLACE)
    fun insert(quoteEntity: QuoteEntity): Long

    @Query("""UPDATE Quotes
                    SET isDeleted = 1, deletedOrder = :deletedOrder
                    WHERE id = :id""")
    fun markAsDeleted(id: Long?, deletedOrder: Int)

    /**
     * Unmark a row that was marked as deleted when a user had swiped it to the left.
     * After this query takes place, the favorite quotes' list will be updated automatically;
     * whereas, there is an observer attached to the getAllQuotes() query.
     */
    @Query("""UPDATE Quotes
                    SET isDeleted = 0, deletedOrder = 0
                    WHERE deletedOrder = :deletedOrder""")
    fun unmarkDeleted(deletedOrder: Int)

}