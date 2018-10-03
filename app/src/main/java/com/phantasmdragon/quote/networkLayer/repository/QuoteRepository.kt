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
package com.phantasmdragon.quote.networkLayer.repository

import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.content.res.Resources
import android.databinding.ObservableField
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.networkLayer.api.QuoteApi
import com.phantasmdragon.quote.utilsLevel.Constant
import javax.inject.Inject

class QuoteRepository @Inject constructor(quoteApi: QuoteApi,
                                          resources: Resources,
                                          sharedPreferences: SharedPreferences)
    : BaseFetchQuoteRepository(quoteApi,
                               resources,
                               sharedPreferences) {

    val quote = MutableLiveData<Quote?>()

    override val queryState = ObservableField<Constant.QueryState>()

    override fun handleQuote(quote: Quote?) {
        this.quote.postValue(quote)
    }

}