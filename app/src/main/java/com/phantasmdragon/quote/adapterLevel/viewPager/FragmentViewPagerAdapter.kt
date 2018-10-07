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
package com.phantasmdragon.quote.adapterLevel.viewPager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.phantasmdragon.quote.presentationLayer.fragment.tab.FavouriteQuoteFragment
import com.phantasmdragon.quote.presentationLayer.fragment.tab.GetQuoteFragment
import com.phantasmdragon.quote.presentationLayer.fragment.tab.SettingsFragment
import java.lang.IllegalStateException
import javax.inject.Inject

class FragmentViewPagerAdapter @Inject constructor(supporFragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(supporFragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> FavouriteQuoteFragment.instantiate()
        1 -> GetQuoteFragment.instantiate()
        2 -> SettingsFragment.instantiate()
        else -> throw IllegalStateException("Unsupported position of ${FragmentViewPagerAdapter::class.java.canonicalName}")
    }

    override fun getCount() = 3

    fun getMiddleItem() = count.div(2)

}