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
package com.phantasmdragon.quote.daggerLevel.module.fragment.favourite

import com.phantasmdragon.quote.adapterLevel.recyclerView.QuoteRecyclerViewAdapter
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.ViewHolderFactory
import com.phantasmdragon.quote.callbackLayer.handler.ClickHandler
import com.phantasmdragon.quote.daggerLevel.annotation.scope.FragmentScope
import com.phantasmdragon.quote.dataLayer.database.SearchableDataSourceFactory
import com.phantasmdragon.quote.dataLayer.repository.DatabaseQuoteRepository
import dagger.Module
import dagger.Provides

@Module
class FavouriteFragmentModule {

    @Provides
    @FragmentScope
    fun provideAdapter(viewHolderFactory: ViewHolderFactory, clickHandler: ClickHandler) =
            QuoteRecyclerViewAdapter(viewHolderFactory, clickHandler)

    @Provides
    @FragmentScope
    fun provideSearchableDataSourceFactory(databaseQuoteRepository: DatabaseQuoteRepository) =
            SearchableDataSourceFactory(databaseQuoteRepository)

}