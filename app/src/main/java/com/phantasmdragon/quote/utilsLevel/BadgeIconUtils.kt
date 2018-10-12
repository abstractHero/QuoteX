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
package com.phantasmdragon.quote.utilsLevel

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.phantasmdragon.quote.R
import javax.inject.Inject

/**
 * It's not good-looking code. It's better to re-write this.
 */
class BadgeIconUtils @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val resources: Resources
) {

    fun getDrawableWithBadge(count: Int, drawableId: Int): Drawable {
        val view = layoutInflater.inflate(R.layout.badge_count, null, false)
        (view.findViewById(R.id.badgeCount_mainIcon) as ImageView).setImageResource(drawableId)

        if (count == 0) {
            val counterTextPanel = view.findViewById(R.id.badgeCount_badgeContainer) as FrameLayout
            counterTextPanel.visibility = View.GONE
        } else {
            val textView = view.findViewById(R.id.badgeCount_badgeText) as TextView
            textView.text = count.toString()
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                     View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        view.setLayerPaint(Paint(Paint.ANTI_ALIAS_FLAG).apply { isAntiAlias = true })
        view.draw(canvas)

        return BitmapDrawable(resources, bitmap)
    }

}