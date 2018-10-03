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
package com.phantasmdragon.quote.callbackLayer.handler

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ActivityContext
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.utilsLevel.toShareView
import javax.inject.Inject

/**
 * We must use an activity context due to the share chooser is started through the context.
 */
class ClickHandler @Inject constructor(@ActivityContext private val context: Context,
                                       private val resources: Resources) {

    fun onShareClick(quote: Quote) {
        context.startActivity(getShareChooser(quote))
    }

    private fun getShareChooser(quote: Quote)
            = Intent.createChooser(getShareIntent(quote),
                                   resources.getString(R.string.share_title))

    private fun getShareIntent(quote: Quote) = Intent().apply {
        type = "text/plain"
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, quote.toShareView())
    }

}