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
package com.phantasmdragon.quote.adapterLevel.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.GetQuoteViewHolder
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.ViewHolderFactory
import com.phantasmdragon.quote.callbackLayer.handler.ClickHandler
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.utilsLevel.ColorUtils

class GetQuoteRecyclerViewAdapter(
    private val viewHolderFactory: ViewHolderFactory,
    private val quoteList: ArrayList<Quote?>,
    private val clickHandler: ClickHandler,
    private val colorUtils: ColorUtils
) : RecyclerView.Adapter<GetQuoteViewHolder>() {

    fun addQuote(quote: Quote?) {
        quoteList.add(quote)
        notifyItemInserted(quoteList.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): GetQuoteViewHolder {
        return viewHolderFactory.createGetQuoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: GetQuoteViewHolder, position: Int) {
        holder.binding.quote = quoteList[position]
        holder.binding.clickHandler = clickHandler
        holder.binding.colorUtils = colorUtils
    }

    override fun getItemCount() = quoteList.size

}