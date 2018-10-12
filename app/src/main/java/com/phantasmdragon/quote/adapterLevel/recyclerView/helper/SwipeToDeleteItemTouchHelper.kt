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
package com.phantasmdragon.quote.adapterLevel.recyclerView.helper

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.QuoteViewHolder
import com.phantasmdragon.quote.callbackLayer.SwipeToDeleteCallback
import java.lang.ref.WeakReference

class SwipeToDeleteItemTouchHelper(
    private val swipeToDeleteCallback: WeakReference<SwipeToDeleteCallback>,
    dragDirs: Int,
    swipeDirs: Int
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        getDefaultUIUtil().clearView(getForegroundView(viewHolder))
    }

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeToDeleteCallback.get()?.onSwiped(getQuoteViewHolder(viewHolder))
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            getDefaultUIUtil().onSelected(getForegroundView(viewHolder))
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        getDefaultUIUtil().onDraw(
            canvas,
            recyclerView,
            getForegroundView(viewHolder),
            dX, dY,
            actionState,
            isCurrentlyActive
        )

    }

    override fun onChildDrawOver(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float, dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        getDefaultUIUtil().onDrawOver(
            canvas,
            recyclerView,
            getForegroundView(viewHolder),
            dX, dY,
            actionState,
            isCurrentlyActive
        )

    }

    private fun getForegroundView(viewHolder: RecyclerView.ViewHolder?) = getQuoteViewHolder(viewHolder).binding.itemQuoteForegroundView

    private fun getQuoteViewHolder(viewHolder: RecyclerView.ViewHolder?) = viewHolder as QuoteViewHolder

}