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
package com.phantasmdragon.quote.presentationLayer.fragment.tab

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.adapterLevel.recyclerView.QuoteRecyclerViewAdapter
import com.phantasmdragon.quote.adapterLevel.recyclerView.helper.SwipeToDeleteItemTouchHelper
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.QuoteViewHolder
import com.phantasmdragon.quote.callbackLayer.MarkAsDeletedCallback
import com.phantasmdragon.quote.callbackLayer.SwipeToDeleteCallback
import com.phantasmdragon.quote.callbackLayer.viewModel.FavouriteQuoteViewModel
import com.phantasmdragon.quote.databinding.FragmentFavouriteBinding
import com.phantasmdragon.quote.utilsLevel.inflateBinding
import com.phantasmdragon.quote.utilsLevel.observe
import com.phantasmdragon.quote.utilsLevel.withViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_favourite.*
import java.lang.ref.WeakReference
import javax.inject.Inject

class FavouriteQuoteFragment :
    DaggerFragment(),
    SwipeToDeleteCallback {

    @Inject lateinit var quoteRecyclerViewAdapter: QuoteRecyclerViewAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var markAsDeletedCallback: MarkAsDeletedCallback
    private lateinit var favouriteQuoteViewModel: FavouriteQuoteViewModel

    override fun onSwiped(viewHolder: QuoteViewHolder) {
        val quoteId = viewHolder.binding.quote?.quoteId

        markAsDeletedCallback.markAsDeleted(quoteId)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        markAsDeletedCallback = context as MarkAsDeletedCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        withViewModel<FavouriteQuoteViewModel>(viewModelFactory) {
            favouriteQuoteViewModel = this

            observe(quotes) {
                isListEmpty.set(it?.isEmpty() == true)
                quoteRecyclerViewAdapter.submitList(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = inflater.inflateBinding<FragmentFavouriteBinding>(R.layout.fragment_favourite, container).apply {
            viewModel = favouriteQuoteViewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecycler()
    }

    private fun initRecycler() {
        fragFavourite_recycler.layoutManager = LinearLayoutManager(context,
                                                                   LinearLayoutManager.VERTICAL,
                                                                   false)
        fragFavourite_recycler.adapter = quoteRecyclerViewAdapter
        fragFavourite_recycler.itemAnimator = DefaultItemAnimator()
        fragFavourite_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        initItemTouchHelper()
    }

    private fun initItemTouchHelper() {
        val swipeToDeleteItemTouchHelper = SwipeToDeleteItemTouchHelper(WeakReference(this),
                                                                        0,
                                                                        ItemTouchHelper.LEFT)

        ItemTouchHelper(swipeToDeleteItemTouchHelper).attachToRecyclerView(fragFavourite_recycler)
    }

    fun submitSearchQuery(searchQuery: CharSequence) {
        val patternString = if (searchQuery.isNotEmpty()) String.format(getString(R.string.pattern_search_any), searchQuery) else searchQuery

        favouriteQuoteViewModel.submitSearchQuery(patternString.toString())
    }

    companion object {
        fun instantiate(bundle: Bundle? = null): FavouriteQuoteFragment = FavouriteQuoteFragment().apply {
            arguments = bundle
        }
    }

}