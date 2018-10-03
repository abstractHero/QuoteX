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
package com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.databinding.ItemGetQuoteBinding
import com.phantasmdragon.quote.databinding.ItemQuoteBinding
import com.phantasmdragon.quote.utilsLevel.inflateBinding
import javax.inject.Inject

class ViewHolderFactory @Inject constructor(private val layoutInflater: LayoutInflater) {

    fun createQuoteViewHolder(parent: ViewGroup): QuoteViewHolder {
        val binding = layoutInflater.inflateBinding<ItemQuoteBinding>(R.layout.item_quote, parent)

        return QuoteViewHolder(binding)
    }

    fun createGetQuoteViewHolder(parent: ViewGroup): GetQuoteViewHolder {
        val binding = layoutInflater.inflateBinding<ItemGetQuoteBinding>(R.layout.item_get_quote, parent)

        return GetQuoteViewHolder(binding)
    }

}