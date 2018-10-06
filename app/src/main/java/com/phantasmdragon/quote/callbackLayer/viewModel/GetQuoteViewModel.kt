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
import android.content.res.Resources
import android.databinding.ObservableField
import com.phantasmdragon.quote.networkLayer.repository.QuoteRepository
import javax.inject.Inject

/**
 * The resources variable CANNOT be private,
 * because it's used in the fragment get quote layout for binding purposes.
 */
class GetQuoteViewModel @Inject constructor(private val quoteRepository: QuoteRepository,
                                            val resources: Resources)
    : ViewModel() {

    val quote = quoteRepository.quote

    /**
     * A certain message shows or not on the layout that depends on the queryState.
     */
    val queryState = quoteRepository.queryState

    /**
     * Used in the layout to control an alpha of the like and dislike images.
     */
    val ratio = ObservableField<Float>()

    init {
        fetchQuote()
    }

    fun fetchQuote() {
        quoteRepository.getQuote()
    }

    override fun onCleared() {
        quoteRepository.dispose()
        super.onCleared()
    }

}