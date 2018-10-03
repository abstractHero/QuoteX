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
package com.phantasmdragon.quote.callbackLayer.bindingAdapter

import android.content.res.Resources
import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.utilsLevel.Constant.QueryState

object BindingAdapter {

    private const val THUMBNAIL_SCALE = 0.25f

    @JvmStatic
    @BindingAdapter("showImageIfError")
    fun ImageView.showImageIfError(queryState: QueryState) {

        if (!isProceed(queryState)) return

        Glide.with(this)
             .load(queryState.chooseImage())
             .thumbnail(THUMBNAIL_SCALE)
             .into(this)
    }

    private fun QueryState.chooseImage() = if (this == QueryState.NO_NETWORK) {
        R.drawable.headsup_no_connection
    } else {
        R.drawable.headsup_error
    }

    private fun View.isProceed(queryState: QueryState): Boolean {

        return if (isError(queryState)) {
            visibility = View.VISIBLE
            true
        } else {
            visibility = View.GONE
            false
        }
    }

    @JvmStatic
    @BindingAdapter("showIfProcess")
    fun View.showIfProcess(queryState: QueryState) {
        visibility = if (queryState == QueryState.PROCESS) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("showIfError")
    fun View.showIfError(queryState: QueryState) {
        visibility = if (isError(queryState)) View.VISIBLE else View.GONE
    }

    private fun isError(queryState: QueryState) = queryState == QueryState.NO_NETWORK || queryState == QueryState.ERROR

    @JvmStatic
    @BindingAdapter("changeLikesAlpha")
    fun ImageView.changeLikesAlpha(ratio: Float) {
        alpha = when {
            ratio >= 0 && alpha == 0f -> return
            ratio >= 0 && alpha != 0f -> 0f
            else -> Math.abs(ratio)
        }
    }

    @JvmStatic
    @BindingAdapter("changeDislikesAlpha")
    fun ImageView.changeDislikesAlpha(ratio: Float) {
        alpha = when {
            ratio <= 0 && alpha == 0f -> return
            ratio <= 0 && alpha != 0f -> 0f
            else -> ratio
        }
    }

    @JvmStatic
    @BindingAdapter("queryState", "setExplanationIfError", requireAll = true)
    fun TextView.showExplanationIfError(queryState: QueryState, resources: Resources) {
        if (!isProceed(queryState)) return

        text = when (queryState) {
            QueryState.NO_NETWORK -> resources.getString(R.string.error_connection)
            else -> resources.getString(R.string.error)
        }
    }

    @JvmStatic
    @BindingAdapter("author", "defaultAuthor", requireAll = true)
    fun TextView.author(author: String, defaultAuthor: String) {
        text = if (author.isEmpty()) defaultAuthor else author.trim()
    }

    @JvmStatic
    @BindingAdapter("showIfEmpty")
    fun View.showIfEmpty(isEmpty: Boolean) {
        visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("loadPlaceholderIfEmpty")
    fun ImageView.loadPlaceholderIfEmpty(isEmpty: Boolean) {
        showIfEmpty(isEmpty)

        if (!isEmpty) return

        Glide.with(this)
             .load(R.drawable.headsup_empty)
             .thumbnail(THUMBNAIL_SCALE)
             .into(this)
    }

}