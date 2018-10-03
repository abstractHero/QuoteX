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

import android.content.SharedPreferences
import android.content.res.Resources
import android.databinding.ObservableField
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.networkLayer.api.QuoteApi
import com.phantasmdragon.quote.networkLayer.exception.NoNetworkException
import com.phantasmdragon.quote.utilsLevel.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseFetchQuoteRepository(private val quoteApi: QuoteApi,
                                        private val resources: Resources,
                                        private val sharedPreferences: SharedPreferences) {

    abstract val queryState: ObservableField<Constant.QueryState>

    private val language: String
        get() = sharedPreferences.getString(resources.getString(R.string.settings_key_quote_language), resources.getString(R.string.default_quote_language))

    fun getQuote() {
        queryState.set(Constant.QueryState.PROCESS)

        quoteApi.getQuote(language).enqueue(quoteHandler)
    }

    private val quoteHandler = object : Callback<Quote> {

        override fun onResponse(call: Call<Quote>?, response: Response<Quote>?) {
            handleQuote(response?.body())
            queryState.set(Constant.QueryState.DONE)
        }

        override fun onFailure(call: Call<Quote>?, throwable: Throwable?) {
            postError(throwable)
        }
    }

    abstract fun handleQuote(quote: Quote?)

    private fun postError(throwable: Throwable?) {
        queryState.set(if (throwable is NoNetworkException) Constant.QueryState.NO_NETWORK
                       else Constant.QueryState.ERROR)
    }

}