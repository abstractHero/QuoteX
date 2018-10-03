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

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ApplicationContext
import javax.inject.Inject

/**
 * Allow getting a random color from the array.xml file,
 * which is used to fill the card background, in the get quote fragment, with a chosen color.
 */
class ColorUtils @Inject constructor(@ApplicationContext private val context: Context,
                                     private val resources: Resources) {

    fun getRandomMaterialColor(): Int {
        val colorTypes = Constant.TypeColor.values()
        val typeColor = colorTypes[getRandomIndex(colorTypes.size)]

        var returnColor = Color.BLACK
        val arrayId = resources.getIdentifier("mdcolor_" + typeColor,
                                              "array",
                                              context.packageName)

        if (arrayId != 0) {
            val colors = resources.obtainTypedArray(arrayId)
            val index = getRandomIndex(colors.length())
            returnColor = colors.getColor(index, Color.BLACK)
            colors.recycle()
        }

        return returnColor
    }

    private fun getRandomIndex(length: Int) = (Math.random() * length).toInt()

}