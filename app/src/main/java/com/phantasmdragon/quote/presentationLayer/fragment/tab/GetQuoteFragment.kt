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
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.adapterLevel.recyclerView.GetQuoteRecyclerViewAdapter
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.ViewHolderFactory
import com.phantasmdragon.quote.callbackLayer.OnLikeListener
import com.phantasmdragon.quote.callbackLayer.handler.ClickHandler
import com.phantasmdragon.quote.callbackLayer.viewModel.GetQuoteViewModel
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ActivityScope
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.databinding.FragmentGetQuoteBinding
import com.phantasmdragon.quote.utilsLevel.ColorUtils
import com.phantasmdragon.quote.utilsLevel.inflateBinding
import com.phantasmdragon.quote.utilsLevel.observe
import com.phantasmdragon.quote.utilsLevel.withViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_get_quote.*
import me.yuqirong.cardswipelayout.CardConfig
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback
import me.yuqirong.cardswipelayout.CardLayoutManager
import me.yuqirong.cardswipelayout.OnSwipeListener
import javax.inject.Inject

@ActivityScope
class GetQuoteFragment @Inject constructor()
    : DaggerFragment() {

    @Inject lateinit var viewHolderFactory: ViewHolderFactory
    @Inject lateinit var clickHandler: ClickHandler
    @Inject lateinit var colorUtils: ColorUtils
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var onLikeListener: OnLikeListener

    private lateinit var getQuoteRecyclerViewAdapter: GetQuoteRecyclerViewAdapter
    private lateinit var cardItemTouchHelperCallback: CardItemTouchHelperCallback<Quote?>
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var getQuoteViewModel: GetQuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        val quoteList = ArrayList<Quote?>()

        getQuoteRecyclerViewAdapter = GetQuoteRecyclerViewAdapter(viewHolderFactory,
                                                                  quoteList,
                                                                  clickHandler,
                                                                  colorUtils)
        cardItemTouchHelperCallback = CardItemTouchHelperCallback(getQuoteRecyclerViewAdapter, quoteList)
        itemTouchHelper = ItemTouchHelper(cardItemTouchHelperCallback)
    }

    private fun initViewModel() {
        withViewModel<GetQuoteViewModel>(viewModelFactory) {
            getQuoteViewModel = this

            observe(quote) {
                getQuoteRecyclerViewAdapter.addQuote(it)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        onLikeListener = context as OnLikeListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = inflater.inflateBinding<FragmentGetQuoteBinding>(R.layout.fragment_get_quote, container).apply {
            viewModel = getQuoteViewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecycler()
    }

    private fun initRecycler() {
        cardItemTouchHelperCallback.setOnSwipedListener(onSwipedListener)

        fragGetQuote_recycler.itemAnimator = DefaultItemAnimator()
        fragGetQuote_recycler.adapter = getQuoteRecyclerViewAdapter
        fragGetQuote_recycler.layoutManager = CardLayoutManager(fragGetQuote_recycler, itemTouchHelper)

        itemTouchHelper.attachToRecyclerView(fragGetQuote_recycler)
    }

    private val onSwipedListener = object : OnSwipeListener<Quote?> {

        override fun onSwiping(viewHolder: RecyclerView.ViewHolder?, ratio: Float, direction: Int) {
            getQuoteViewModel.ratio.set(ratio)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, quote: Quote?, direction: Int) {
            if (quote == null) return

            if (direction == CardConfig.SWIPED_LEFT) onLikeListener.onLike(quote)

            getQuoteViewModel.fetchQuote()
            getQuoteViewModel.ratio.set(0f)
        }

        override fun onSwipedClear() {}
    }

}