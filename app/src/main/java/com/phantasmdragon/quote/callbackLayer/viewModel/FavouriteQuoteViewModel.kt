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
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.databinding.ObservableField
import com.phantasmdragon.quote.dataLayer.database.SearchableDataSourceFactory
import javax.inject.Inject

class FavouriteQuoteViewModel @Inject constructor(private val searchableDataSourceFactory: SearchableDataSourceFactory) :
    ViewModel() {

    /**
     * LiveData with the paged list implementation that observes the changes in the database.
     */
    val quotes =
            LivePagedListBuilder(searchableDataSourceFactory,
                                 PagedList.Config.Builder()
                                                 .setPageSize(PAGE_SIZE)
                                                 .setEnablePlaceholders(ENABLE_PLACEHOLDER)
                                                 .build())
                                 .build()

    /**
     * Used to show a message if there is no any quote in the list yet.
     */
    val isListEmpty = ObservableField<Boolean>(true)

    fun submitSearchQuery(searchQuery: String) {
        searchableDataSourceFactory.searchQuery = searchQuery
        quotes.value?.dataSource?.invalidate()
    }

    companion object {
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDER = true
    }

}