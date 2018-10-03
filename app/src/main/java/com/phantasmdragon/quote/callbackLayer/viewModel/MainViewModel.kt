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
package com.phantasmdragon.quote.callbackLayer.viewModel

import android.arch.lifecycle.ViewModel
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.dataLayer.repository.DatabaseQuoteRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val databaseQuoteRepository: DatabaseQuoteRepository)
    : ViewModel() {

    var currentPosition = -1
    var badgeCount = 0
    var undoBadgeCount = 0

    fun insert(quote: Quote) {
        databaseQuoteRepository.insert(quote)
    }

    fun markAsDeleted(id: Long?, deletedOrder: Int) {
        databaseQuoteRepository.markAsDeleted(id, deletedOrder)
    }

    fun unmarkDeleted(deletedOrder: Int) {
        databaseQuoteRepository.unmarkDeleted(deletedOrder)
    }

    fun deleteAllMarked() {
        databaseQuoteRepository.deleteAllMarked()
    }

}