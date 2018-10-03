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

import android.annotation.SuppressLint
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.QuoteViewHolder
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.ViewHolderFactory
import com.phantasmdragon.quote.callbackLayer.handler.ClickHandler
import com.phantasmdragon.quote.dataLayer.json.Quote
import javax.inject.Inject

class QuoteRecyclerViewAdapter @Inject constructor(private val viewHolderFactory: ViewHolderFactory,
                                                   private val clickHandler: ClickHandler)
    : PagedListAdapter<Quote, QuoteViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Quote>() {

            override fun areItemsTheSame(oldItem: Quote, newItem: Quote) = oldItem.quoteId == newItem.quoteId

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote) = oldItem == newItem

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): QuoteViewHolder {
        return viewHolderFactory.createQuoteViewHolder(parent)
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val bounceAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.abc_grow_fade_in_from_bottom)

        holder.binding.quote = getItem(position)
        holder.binding.clickHandler = clickHandler

        holder.itemView.startAnimation(bounceAnimation)
    }

}